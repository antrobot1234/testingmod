package mod.antrobot.anttm.capabilities.basicio;

import java.util.concurrent.Callable;

public class BasicFactory implements Callable<IBasicIO> {
    public enum EnumDir {up,down,left,right}
    public IBasicIO call() throws Exception {

        return new IBasicIO() {
            EnumDir input = EnumDir.valueOf("left");
            boolean output = true;
            @Override
            public EnumDir getInput() {
                return input;
            }
            @Override
            public void setInput(String i){
                input = EnumDir.valueOf(i.toLowerCase());
            }
            @Override
            public void setInput(EnumDir i){
                input = i;

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
                switch(input) {
                    case left: return calcLeft(slot);
                    case right: return calcRight(slot);
                    case up: return calcUp(slot);
                    case down: return calcDown(slot);
                }
                return -1;
            }
            @Override
            public int calcOutput(int slot) {
                if(!output)return -1;
                switch (input) {
                    case left: return calcRight(slot);
                    case right: return calcLeft(slot);
                    case up: return calcDown(slot);
                    case down: return calcUp(slot);
                }
                return -1;
            }

            @Override
            public boolean isInvalid(int slot) {
                return calcInput(slot)==-1 || calcOutput(slot)==-1;
            }

            @Override
            public void cycleInput(){
                switch(input){
                    case up: input = EnumDir.right;return;
                    case left: input = EnumDir.up;return;
                    case down: input = EnumDir.left;return;
                    case right: input = EnumDir.down;return;
                }
            }
        };
    }
}
