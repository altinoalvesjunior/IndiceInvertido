package Interno;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Busca {

    Indice indice = new Indice();

    public Busca() throws IOException {

    }

    public void buscar(String palavra) {
        StringTokenizer st;
        String aux;

        ArrayList<String> pesquisa = new ArrayList<>();

        palavra = palavra.toLowerCase(); // para facilitar na busca, a palavra é convertida para LowerCase, assim não há problemas de não encontrar
        st = new StringTokenizer(palavra, "\" / \\, . () + : ; * = -  — |");

        //enquanto existir tokens
        while(st.hasMoreTokens()){
            aux = st.nextToken().toLowerCase(); //sempre converte os próximos tokens para minúsculo como ocorreu na palvra

            if(!(indice.getListaStopWords().contains(aux))){
                if(!(pesquisa.contains(aux))){
                    pesquisa.add(aux);
                }
            }
        }

        for(String palavraInserida : pesquisa) {
            System.out.println();
            System.out.println("Palavra digitada: " + palavraInserida);
            if(indice.getMapa().containsKey(palavraInserida))
                System.out.println("A palavra digitada encontra-se no(s) arquivo(s) de número: " + indice.getMapa().get(palavraInserida).ocorrencias());
            else System.out.println("A palavra digitada não encontra-se em nenhum dos arquivos da pasta!");
        }
    }

}