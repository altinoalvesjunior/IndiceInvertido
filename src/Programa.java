import Interno.Busca;

import java.io.IOException;
import java.util.Scanner;

public class Programa {

    public static void main(String[] args) throws IOException {
        Busca busca = new Busca();

        Scanner sc = new Scanner(System.in);

        int op;

        do{
            System.out.print("\n\nDigite a palavra(s) que deseja buscar: ");
            String palavra = sc.nextLine();
            busca.buscar(palavra);

            System.out.print("\n");
            System.out.println("Deseja pesquisar mais alguma palavra? ");
            System.out.println("Caso não, digite 1");
            System.out.println("Se sim, digite qualquer outro número");
            System.out.print("\n Escolha: ");
            op = sc.nextInt();
            sc.nextLine();
        } while (op != 1);

        sc.close();
    }
}