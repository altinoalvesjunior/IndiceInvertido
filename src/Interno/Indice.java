package Interno;

import java.io.*;
import java.util.*;

public class Indice extends HashMap<String, Ocorrencia> {

    private static int quantidade = 0;
    public HashMap <String, Ocorrencia> mapa;
    public ArrayList<String> listaStopWords = new ArrayList<>();

    public ArrayList<String> getListaStopWords(){
        return this.listaStopWords;
    }

    public HashMap<String, Ocorrencia> getMapa(){
        return this.mapa;
    }

    public void verificaArquivoIndice() throws IOException{
        if(!(new File("IndiceInvertido.bin").exists()))
            criarArquivoIndice();
        preencherArquivoIndice();
    }

    public Indice() throws IOException {
        mapa = new HashMap<>();
        lerArquivoStopWords(listaStopWords);
        verificaArquivoIndice();
    }

    public void lerArquivoStopWords(ArrayList<String> lista) {
        try (BufferedReader br = new BufferedReader(new FileReader("stopwords.txt"))) {

            String itemArquivo = br.readLine();

            while (itemArquivo != null) {
                String[] campos = itemArquivo.split(",");
                String palavra = campos[0];

                lista.add(palavra);
                itemArquivo = br.readLine();
            }
        }

        catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void preencherArquivoIndice() throws IOException {
        int i, j;

        RandomAccessFile ram = new RandomAccessFile("IndiceInvertido.bin", "rw");

        quantidade = ram.readInt();

        for(i = 1; i<= quantidade;i++) {
            String item = ram.readUTF();

            ArrayList<Integer> numeroOcorrencias = new ArrayList<>();

            int quantidade = ram.readInt();
            for(j = 1; j <= quantidade; j++){
                numeroOcorrencias.add(ram.readInt());
            }

            mapa.put(item, new Ocorrencia(numeroOcorrencias));
        }

        ram.close();
    }

    public void criarArquivoIndice() throws IOException {
        String aux;

        File pasta = new File("./arquivosTextos");
        File[] listaDeArquvios = pasta.listFiles();

        for (int i = 0; i < listaDeArquvios.length; i++) {
            File arquivo = listaDeArquvios[i];
            if (arquivo.isFile() && arquivo.getName().endsWith(".txt")) {

                try(BufferedReader br = new BufferedReader(new FileReader(arquivo))){
                    String itemArquivo = br.readLine();

                    while(itemArquivo != null){
                        StringTokenizer st = new StringTokenizer(itemArquivo, "\" / \\, . () + : ; * = -  — |");

                        while(st.hasMoreTokens()){
                            aux = removerCaractereEspecial(st.nextToken());

                            if(aux != " " && aux != ""){
                                if(!(listaStopWords.contains(aux))){
                                    if(mapa.containsKey(aux)){
                                        if(!(mapa.get(aux).verificaExisteOcorrencia(i+1)))
                                            mapa.get(aux).adicionarOcorrenciaArquivo(i+1);
                                    }
                                    else {
                                        mapa.put(aux, new Ocorrencia(i+1));
                                        quantidade++;
                                    }
                                }
                            }
                        }
                        itemArquivo = br.readLine();
                    }
                } catch (final IOException e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            }
        }
        escreverArquivoBinario();
    }

    public String removerCaractereEspecial(String caractere){
        String palavra = caractere.replace(".", "");
        palavra = palavra.replace("(", "");
        palavra = palavra.replace(")", "");
        palavra = palavra.replace("!", "");
        palavra = palavra.replace("?", "");
        palavra = palavra.replace(":", "");
        palavra = palavra.replace(";", "");
        palavra = palavra.replace(",", "");
        palavra = palavra.replace("\'", "");
        palavra = palavra.replace("\"", "");
        palavra = palavra.toLowerCase();
        palavra = palavra.replace("à", "a");
        palavra = palavra.replace("ã", "a");
        palavra = palavra.replace("á", "a");
        palavra = palavra.replace("é", "e");
        palavra = palavra.replace("ê", "e");
        palavra = palavra.replace("í", "i");
        palavra = palavra.replace("ó", "o");
        palavra = palavra.replace("õ", "o");
        palavra = palavra.replace("ú", "u");
        palavra = palavra.replace("ç", "c");

        return palavra;
    }

    public void escreverArquivoBinario() throws IOException{
        RandomAccessFile ram = new RandomAccessFile(new File("IndiceInvertido.bin"), "rw");

        ram.writeInt(quantidade);

        for(Map.Entry<String, Ocorrencia> map : mapa.entrySet()) {
            ram.writeUTF((String)map.getKey());
            ram.writeInt(mapa.get(map.getKey()).ocorrencia.size());

            for(Integer oc : mapa.get(map.getKey()).ocorrencia){
                ram.writeInt(oc);
            }
        }
    }

}
