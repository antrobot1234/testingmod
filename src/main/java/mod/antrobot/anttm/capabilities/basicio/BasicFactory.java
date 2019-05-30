package mod.antrobot.anttm.capabilities.basicio;

import net.minecraft.nbt.NBTTagCompound;

import java.util.concurrent.Callable;

public class BasicFactory implements Callable<IBasicIO> {
    @Override
    public IBasicIO call() throws Exception {
        return new IBasicIO() {
            String input = "left";
            boolean output = true;

            @Override
            public boolean validInput(String i){
                String[] possibilities = "left right up down".split(" ");
                for(String s: possibilities){
                    if(s.equals(i))return true;
                }
                return false;
            }
            @Override
            public String getInput() {
                return this.input;
            }
            @Override
            public void setInput(String i){
                if(!validInput(i))throw new IllegalArgumentException("input is not one of the four valid responses: up, down, left, right");
                input = i.toLowerCase();
            }

            @Override
            public boolean getOutput(){
                return output;
            }
            @Override
            public void setOutput(boolean i) {
                output = i;
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

            @Override
            public int calcInput(int slot) {
                if(input.equals("left"))return calcLeft(slot);
                if(input.equals("right"))return calcRight(slot);
                if(input.equals("up"))return calcUp(slot);
                if(input.equals("down"))return calcDown(slot);
                return -1;
            }
            @Override
            public int calcOutput(int slot) {
                if(!output)return -1;
                if(input.equals("left"))return calcRight(slot);
                if(input.equals("right"))return calcLeft(slot);
                if(input.equals("up"))return calcDown(slot);
                if(input.equals("down"))return calcUp(slot);
                return -1;
            }

            @Override
            public boolean isInvalid(int slot) {
                return calcInput(slot)==-1 || calcOutput(slot)==-1;
            }

            @Override
            public void cycleInput(){
                if(input.equals("left"))input = "up";
                else if(input.equals("right"))input = "down";
                else if(input.equals("up"))input = "right";
                else if(input.equals("down"))input = "left";
            }
        };
    }
}
