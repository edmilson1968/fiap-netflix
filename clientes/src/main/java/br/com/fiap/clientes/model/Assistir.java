package br.com.fiap.clientes.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Assistir implements Serializable {

    private Long id;

    private Long filmeId;

    private Long clienteId;

    private LocalDateTime dataInicio;

    private LocalDateTime dataFim;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getFilmeId() {
        return filmeId;
    }

    public void setFilmeId(Long filmeId) {
        this.filmeId = filmeId;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Assistir{");
        sb.append("id=").append(id);
        sb.append(", filmeId=").append(filmeId);
        sb.append(", clienteId=").append(clienteId);
        sb.append(", dataInicio=").append(dataInicio);
        sb.append(", dataFim=").append(dataFim);
        sb.append('}');
        return sb.toString();
    }



}
