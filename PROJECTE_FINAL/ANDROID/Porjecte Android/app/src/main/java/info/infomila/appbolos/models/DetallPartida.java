package info.infomila.appbolos.models;

import java.util.Date;

/**
 * Created by alber
 */

public class DetallPartida {

    private int partidaId;
    private int entrada;
    private Date dataEntrada;
    private int sociId;
    private String sociNom;
    private String sociTag;
    private int caramboles;

    public DetallPartida(int partidaId, int entrada, Date dataEntrada, int sociId, String sociNom, String sociTag, int caramboles) {
        setPartidaId(partidaId);
        setEntrada(entrada);
        setDataEntrada(dataEntrada);
        setSociId(sociId);
        setSociNom(sociNom);
        setSociTag(sociTag);
        setCaramboles(caramboles);
    }

    public DetallPartida(DetallPartida dp) {
        setPartidaId(dp.partidaId);
        setEntrada(dp.entrada);
        setDataEntrada(dp.dataEntrada);
        setSociId(dp.sociId);
        setSociNom(dp.sociNom);
        setSociTag(dp.sociTag);
        setCaramboles(dp.caramboles);
    }

    public int getPartidaId() {
        return partidaId;
    }

    public void setPartidaId(int partidaId) {
        this.partidaId = partidaId;
    }

    public int getEntrada() {
        return entrada;
    }

    public void setEntrada(int entrada) {
        this.entrada = entrada;
    }

    public Date getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public int getSociId() {
        return sociId;
    }

    public void setSociId(int sociId) {
        this.sociId = sociId;
    }

    public String getSociNom() {
        return sociNom;
    }

    public void setSociNom(String sociNom) {
        this.sociNom = sociNom;
    }

    public String getSociTag() {
        return sociTag;
    }

    public void setSociTag(String sociTag) {
        this.sociTag = sociTag;
    }

    public int getCaramboles() {
        return caramboles;
    }

    public void setCaramboles(int caramboles) {
        this.caramboles = caramboles;
    }
}
