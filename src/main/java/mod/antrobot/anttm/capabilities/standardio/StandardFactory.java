package mod.antrobot.anttm.capabilities.standardio;

import java.util.*;
import java.util.stream.Collectors;
import java.util.concurrent.Callable;
import mod.antrobot.anttm.capabilities.standardio.EnumIO.*;
import mod.antrobot.anttm.util.ModReference;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;


public class StandardFactory implements Callable<IStandardIO> {
    @GameRegistry.ObjectHolder(ModReference.ID+":extender")
    private static final Item extender = null;
    @Override
    public IStandardIO call() throws Exception {
        return new IStandardIO() {
            HashMap<EnumDir, EnumType> map = new HashMap<>();
            HashMap<EnumType,EnumDir> current = new HashMap<>();

            @Override
            public void initMap(EnumType up, EnumType right, EnumType down, EnumType left) {
                setDir(EnumDir.up,up);
                setDir(EnumDir.right,right);
                setDir(EnumDir.down,down);
                setDir(EnumDir.left,left);
                fillCurrent();
            }
            private void fillCurrent(){
                current.putIfAbsent(EnumType.input,EnumDir.nil);
                current.putIfAbsent(EnumType.output,EnumDir.nil);
            }
            @Override
            public HashMap<EnumDir,EnumType> getSides() {
                return map;
            }

            @Override
            public HashMap<EnumDir,EnumType> getSidesOf(EnumType type) {
                return (HashMap<EnumDir,EnumType>)map.entrySet().stream()
                        .filter(x->x.getValue()==type)
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            }

            @Override
            public HashMap<EnumDir, Integer> getSlots(InventoryPlayer inv,int slot) {
                HashMap<EnumDir,Integer> output = new HashMap<>();
                for(HashMap.Entry<EnumDir,EnumType> entry: map.entrySet()){
                    output.put(entry.getKey(),calcFaceExtend(inv,slot,entry.getKey()));
                }
                return output;
            }

            @Override
            public HashMap<EnumDir, Integer> getSlotsOf(InventoryPlayer inv, int slot,EnumType type) {
                HashMap<EnumDir,Integer> output = new HashMap<>();
                for(HashMap.Entry<EnumDir,EnumType> entry: map.entrySet()){
                    if(entry.getValue()==type){
                    output.put(entry.getKey(),calcFaceExtend(inv,slot,entry.getKey()));
                    }
                }
                return output;
            }

            @Override
            public int getAmount(EnumType type) {
                return getSidesOf(type).size();
            }

            @Override
            public void setDir(EnumDir dir, EnumType type) {
                map.put(dir,type);
                if(type==EnumType.nil&&current.get(type)==dir){
                    if(map.containsValue(type)){
                        current.put(type,find(dir,type,true));
                    } else {current.put(type,EnumDir.nil);}
                }
                else if(current.getOrDefault(type,EnumDir.nil)==EnumDir.nil){
                    if(type != EnumType.nil){
                        current.put(type,dir);
                    }
                }
            }

            @Override
            public void setDirForce(EnumDir dir, EnumType type) {
                map.put(dir,type);
                if(type==EnumType.nil&&current.get(type)==dir){
                    current.put(type,EnumDir.nil);
                } else {
                    current.put(type,dir);
                }
            }

            @Override
            public EnumType getFace(EnumDir dir) {
                return map.get(dir);
            }

            @Override
            public boolean isType(EnumDir dir, EnumType type) {
                return map.get(dir) == type;
            }

            @Override
            public boolean isValidDir(EnumDir dir) {
                return map.get(dir) != EnumType.nil;
            }

            @Override
            public int calcLeft(int slot) {
                if(slot%9==0){
                    return -1;
                }
                return slot-1;
            }
            @Override
            public int calcRight(int slot) {
                if(slot%9==8)return -1;
                return slot+1;
            }
            @Override
            public int calcUp(int slot) {
                if(slot<=17&&slot>=8)return -1;
                if((slot+1)/9==0)return slot+27;
                return slot-9;
            }
            @Override
            public int calcDown(int slot) {
                if((slot+1)/9==0)return -1;
                if(slot<=35&&slot>=27)return slot-27;
                return slot+9;
            }

            private int calcFace(int slot, EnumDir dir) {
                switch(dir){
                    case up: return calcUp(slot);
                    case down: return calcDown(slot);
                    case left: return calcLeft(slot);
                    case right: return calcRight(slot);
                    default: return -1;
                }
            }

            @Override
            public int calcFaceExtend(InventoryPlayer inv, int slot, EnumDir dir) {
                int calc = calcFace(slot,dir);
                if(calc ==-1)return calc;
                if(inv.getStackInSlot(calc).getItem()==extender)return(calcFaceExtend(inv,calc,dir));
                else return calc;
            }

            @Override
            public boolean isValidSlot(InventoryPlayer inv, int slot) {
                for(HashMap.Entry entry: map.entrySet()){
                    if(entry.getValue()!=EnumType.nil){
                        if(calcFaceExtend(inv,slot,(EnumDir)entry.getKey())==-1)return false;
                    }
                }
                return true;
            }
            private Vector<EnumDir> sortedArray(){
                Vector<EnumDir> output = new Vector<>(4);
                output.add(EnumDir.up);output.add(EnumDir.right);
                output.add(EnumDir.down);output.add(EnumDir.left);
                return output;
            }
            @Override
            public EnumDir find(EnumDir dir,EnumType type,boolean next){
                Vector<EnumDir> vector = sortedArray();
                int size = vector.size();
                int i = vector.indexOf(dir);
                if(next)i=(i+1)%size;
                for(int k=i;true;k=(k+1)%size){
                    if(map.get(vector.get(k))==type)return vector.get(k);
                }
            }

            @Override
            public void cycle(EnumType type){
                if(!map.containsValue(EnumType.nil))return;
                if(!map.containsValue(type))return;
                if(type == EnumType.nil)return;
                EnumDir dir = find(EnumDir.up,type,false);
                setDirForce(dir,EnumType.nil);
                setDir(find(dir,EnumType.nil,true),type);
            }
            @Override
            public void rotate() {
                Vector<EnumDir> arr = sortedArray();
                EnumType first = map.get(arr.get(0));
                for(int i=0;i<arr.size()-1;i++){
                    map.put(arr.get(i),map.get(arr.get(i+1)));
                }
                map.put(arr.get(0),first);

                rotateCurrent(EnumType.input);
                rotateCurrent(EnumType.output);
            }

            @Override
            public void rotateCurrent(EnumType type) {
                if(type == EnumType.nil)return;
                if(current.get(type)==EnumDir.nil)return;
                Vector<EnumDir> vector = sortedArray();
                EnumDir dir = current.get(type);
                if(dir!=EnumDir.nil){
                    int i = vector.indexOf(dir);
                    current.put(type, vector.get((i+1)%4) );}
            }

            @Override
            public EnumDir getCurrent(EnumType type) {
                return current.get(type);
            }

            @Override
            public void setCurrent(EnumType type, EnumDir dir) {
                current.put(type,dir);
            }

            @Override
            public EnumDir getNext(EnumType type) {
                if(current.get(type)==EnumDir.nil)return EnumDir.nil;
                EnumDir newDir = find(current.get(type),type,true);
                current.put(type,newDir);
                return newDir;
            }
        };
    }
}
