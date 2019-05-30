package mod.antrobot.anttm.capabilities.basicio;

public interface IBasicIO {
    String input = "left";
    boolean output = false;

    String getInput();
    void setInput(String i);

    boolean getOutput();
    void setOutput(boolean i);

    int calcLeft(int slot);
    int calcRight(int slot);
    int calcUp(int slot);
    int calcDown(int slot);

    int calculateInput(int slot);
    int calculateOutput(int slot);

    boolean isValid(int slot);
}
