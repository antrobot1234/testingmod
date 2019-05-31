package mod.antrobot.anttm.capabilities.basicio;

public interface IBasicIO{

    BasicFactory.InputFacing getInput();
    void setInput(String i);
    void setInput(BasicFactory.InputFacing i);

    boolean getOutput();
    void setOutput(boolean i);

    int calcLeft(int slot);
    int calcRight(int slot);
    int calcUp(int slot);
    int calcDown(int slot);

    int calcInput(int slot);
    int calcOutput(int slot);

    boolean isInvalid(int slot);

    void cycleInput();
}
