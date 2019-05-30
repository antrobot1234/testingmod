package mod.antrobot.anttm.capabilities.basicio;

public interface IBasicIO {
    boolean validInput(String i);

    String getInput();
    void setInput(String i);

    boolean getOutput();
    void setOutput(boolean i);

    int calcLeft(int slot);
    int calcRight(int slot);
    int calcUp(int slot);
    int calcDown(int slot);

    int calcInput(int slot);
    int calcOutput(int slot);

    boolean isValid(int slot);

    void cycleInput();
}
