package com.example.tallertransaccion;

public class cuenta {

    private String usuario;
    private String nrocuenta;
    private String fecha;
    private String saldo1;
    private String cuentadestino;

    public String getCuentadestino() {
        return cuentadestino;
    }

    public void setCuentadestino(String cuentadestino) {
        this.cuentadestino = cuentadestino;
    }

    public cuenta (){

    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNrocuenta() {
        return nrocuenta;
    }

    public void setNrocuenta(String nrocuenta) {
        this.nrocuenta = nrocuenta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getSaldo1() {
        return saldo1;
    }

    public void setSaldo1(String saldo1) {
        this.saldo1 = saldo1;
    }
}


