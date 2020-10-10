package Interno;

import java.util.ArrayList;

public class Ocorrencia {
    ArrayList<Integer> ocorrencia;

    public Ocorrencia(Integer arquivo){
        ocorrencia = new ArrayList<Integer>();
        ocorrencia.add(arquivo);
    }

    public Ocorrencia(ArrayList<Integer> ocorrencias){
        this.ocorrencia = ocorrencias;
    }

    public String ocorrencias() {

        String arquivosOcorrencia = "";

        for(Integer i: this.ocorrencia){
            arquivosOcorrencia += (" [" + i +"] ");
        }

        return arquivosOcorrencia;
    }

    public boolean verificaExisteOcorrencia(Integer arquivo){
        return this.ocorrencia.contains(arquivo);
    }

    public void adicionarOcorrenciaArquivo(Integer arquivo){
        this.ocorrencia.add(arquivo);
    }

}
