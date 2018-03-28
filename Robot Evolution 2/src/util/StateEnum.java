package util;

public enum StateEnum {

    WAIT(1), DOING(2), DONE(3);

    private final int valor;
    StateEnum(int valorOpcao){
        valor = valorOpcao;
    }
    public int getValor(){
        return valor;
    }

}
