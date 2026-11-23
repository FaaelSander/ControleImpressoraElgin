import com.sun.jna.Library;
import com.sun.jna.Native;
import java.util.Scanner;
import java.nio.file.*;
import java.io.IOException;

public class Main {

    private static final String USUARIO = "admin";
    private static final String SENHA = "1234";
    private static final String ASS_QRCODE = "Q5DLkpdRijIRGY6YSSNsTWK1TztHL1vD0V1Jc4spo/CEUqICEb9SFy82ym8EhBRZjbh3btsZhF+sjHqEMR159i4agru9x6KsepK/q0E2e5xlU5cv3m1woYfgHyOkWDNcSdMsS6bBh2Bpq6s89yJ9Q6qh/J8YHi306ce9Tqb/drKvN2XdE5noRSS32TAWuaQEVd7u+TrvXlOQsE3fHR1D5f1saUwQLPSdIv01NF6Ny7jZwjCwv1uNDgGZONJdlTJ6p0ccqnZvuE70aHOI09elpjEO6Cd+orI7XHHrFCwhFhAcbalc+ZfO5b/+vkyAHS6CYVFCDtYR9Hi5qgdk31v23w==";

    private static boolean conexaoAberta = false;
    private static int tipo;
    private static String modelo;
    private static String conexao;
    private static int parametro;

    private static final Scanner scanner = new Scanner(System.in);

    public interface ImpressoraDLL extends Library {
        ImpressoraDLL INSTANCE = (ImpressoraDLL) Native.load(
                Paths.get("E1_Impressora01.dll").toAbsolutePath().toString(), ImpressoraDLL.class);

        int AbreConexaoImpressora(int tipo, String modelo, String conexao, int param);
        int FechaConexaoImpressora();
        int ImpressaoTexto(String dados, int posicao, int estilo, int tamanho);
        int Corte(int avanco);
        int ImpressaoQRCode(String dados, int tamanho, int nivelCorrecao);
        int ImpressaoCodigoBarras(int tipo, String dados, int altura, int largura, int HRI);
        int AvancaPapel(int linhas);
        int StatusImpressora(int param);
        int AbreGavetaElgin(int pino, int ti, int tf);
        int AbreGaveta(int pino, int ti, int tf);
        int SinalSonoro(int qtd, int tempoInicio, int tempoFim);
        int ModoPagina();
        int LimpaBufferModoPagina();
        int ImprimeModoPagina();
        int ModoPadrao();
        int PosicaoImpressaoHorizontal(int posicao);
        int PosicaoImpressaoVertical(int posicao);
        int ImprimeXMLSAT(String dados, int param);
        int ImprimeXMLCancelamentoSAT(String dados, String assQRCode, int param);
    }

    private static String capturarEntrada(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }

    private static int lerInteiro(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Por favor, digite apenas números.");
            }
        }
    }

    private static String lerArquivo(String nome) {
        try {
            return new String(Files.readAllBytes(Paths.get(nome)));
        } catch (IOException e) {
            System.out.println("Não foi possível ler o arquivo: " + nome);
            return null;
        }
    }

    public static boolean fazerLogin() {
        for (int tentativa = 1; tentativa <= 3; tentativa++) {
            String u = capturarEntrada("Usuário: ");
            String s = capturarEntrada("Senha: ");

            if (u.equals(USUARIO) && s.equals(SENHA)) {
                System.out.println("Login efetuado com sucesso!\n");
                return true;
            } else {
                int restam = 3 - tentativa;
                System.out.println("Usuário ou senha incorretos. Tentativas restantes: " + restam + "\n");
            }
        }
        System.out.println("Acesso bloqueado. Número de tentativas excedido.");
        return false;
    }

    public static void configurarConexao() {
        System.out.println("\n--- Configurar Conexão ---");
        tipo = lerInteiro("Tipo de conexão (1=USB, 2=Serial, 3=Rede, 4=Bluetooth): ");
        modelo = capturarEntrada("Modelo (ex: i8 ou i9): ");
        conexao = capturarEntrada("Conexão (ex: USB): ");
        parametro = lerInteiro("Parâmetro (0 para USB): ");
        System.out.println("Configurações salvas.");
    }

    public static void abrirConexao() {
        if (!conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.AbreConexaoImpressora(tipo, modelo, conexao, parametro);
            if (retorno == 0) {
                conexaoAberta = true;
                System.out.println("Conexão aberta com sucesso.");
            } else {
                System.out.println("Erro ao abrir conexão. Código de erro: " + retorno);
            }
        } else {
            System.out.println("Conexão já está aberta.");
        }
    }

    public static void fecharConexao() {
        if (conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.FechaConexaoImpressora();
            if (retorno == 0) {
                conexaoAberta = false;
                System.out.println("Conexão fechada com sucesso.");
            } else {
                System.out.println("Erro ao fechar conexão. Código: " + retorno);
            }
        }
    }

    public static void imprimirTexto() {
        if (!conexaoAberta) {
            System.out.println("Conexão fechada. Abra a conexão antes (opção 2).");
            return;
        }
        String texto = capturarEntrada("Texto a imprimir: ");
        int copias = 1;

        for (int i = 1; i <= copias; i++) {
            int retorno = ImpressoraDLL.INSTANCE.ImpressaoTexto(texto, 1, 4, 0);
            if (retorno >= 0) {
                System.out.println("Cópia " + i + " impressa!");
            } else {
                System.out.println("Erro ao imprimir. Código: " + retorno);
            }
        }
        ImpressoraDLL.INSTANCE.Corte(5);
    }

    public static void imprimirQRCode() {
        if (!conexaoAberta) {
            System.out.println("Conexão fechada. Abra a conexão antes (opção 2).");
            return;
        }
        String dados = capturarEntrada("Digite o conteúdo do QR Code: ");
        int retorno = ImpressoraDLL.INSTANCE.ImpressaoQRCode(dados, 6, 4);

        if (retorno == 0) {
            System.out.println("QR Code impresso com sucesso!");
        } else {
            System.out.println("Erro ao imprimir QR Code. Código: " + retorno);
        }
        ImpressoraDLL.INSTANCE.Corte(5);
    }

    public static void imprimirCodigoBarras() {
        if (!conexaoAberta) {
            System.out.println("Conexão fechada. Abra a conexão antes (opção 2).");
            return;
        }
        int retorno = ImpressoraDLL.INSTANCE.ImpressaoCodigoBarras(8, "{A012345678912", 100, 2, 3);

        if (retorno == 0) {
            System.out.println("Código de barras impresso com sucesso!");
        } else {
            System.out.println("Erro ao imprimir Código de Barras. Código: " + retorno);
        }
        ImpressoraDLL.INSTANCE.Corte(5);
    }

    public static void imprimirXMLSAT() {
        if (!conexaoAberta) {
            System.out.println("Conexão fechada. Abra a conexão antes (opção 2).");
            return;
        }
        String xml = lerArquivo("XMLSAT.xml");

        if (xml != null) {
            int retorno = ImpressoraDLL.INSTANCE.ImprimeXMLSAT(xml, 0);
            if (retorno == 0) {
                System.out.println("XML do SAT impresso com sucesso!");
            } else {
                System.out.println("Erro ao imprimir XML SAT. Código: " + retorno);
            }
        }
        ImpressoraDLL.INSTANCE.Corte(5);
    }

    public static void imprimirXMLCancelamentoSAT() {
        if (!conexaoAberta) {
            System.out.println("Conexão fechada. Abra a conexão antes (opção 2).");
            return;
        }
        String xml = lerArquivo("CANC_SAT.xml");

        if (xml != null) {
            int retorno = ImpressoraDLL.INSTANCE.ImprimeXMLCancelamentoSAT(xml, ASS_QRCODE, 0);
            if (retorno == 0) {
                System.out.println("XML de Cancelamento SAT impresso com sucesso!");
            } else {
                System.out.println("Erro ao imprimir XML Cancelamento. Código: " + retorno);
            }
        }
        ImpressoraDLL.INSTANCE.Corte(5);
    }

    public static void abrirGaveta() {
        if (!conexaoAberta) {
            System.out.println("Conexão fechada. Abra a conexão antes (opção 2).");
            return;
        }
        int retorno = ImpressoraDLL.INSTANCE.AbreGaveta(1, 5, 10);
        if (retorno == 0) {
            System.out.println("Gaveta aberta com sucesso!");
        } else {
            System.out.println("Erro ao abrir a gaveta. Código: " + retorno);
        }
    }

    public static void sinalSonoro() {
        if (!conexaoAberta) {
            System.out.println("Conexão fechada. Abra a conexão antes (opção 2).");
            return;
        }
        int quantidade = lerInteiro("Quantos bips? ");
        int retorno = ImpressoraDLL.INSTANCE.SinalSonoro(quantidade, 50, 5);
        if (retorno == 0) {
            System.out.println("Bip!");
        } else {
            System.out.println("Erro no sinal sonoro. Código: " + retorno);
        }
    }

    public static void avancarPapel() {
        if (!conexaoAberta) {
            System.out.println("Conexão fechada. Abra a conexão antes (opção 2).");
            return;
        }
        int linhas = lerInteiro("Avançar quantas linhas? ");
        int retorno = ImpressoraDLL.INSTANCE.AvancaPapel(linhas);
        if (retorno == 0) {
            System.out.println("Papel avançado.");
        } else {
            System.out.println("Erro ao avançar papel. Código: " + retorno);
        }
    }

    public static void exibirMenu() {
        System.out.println("\n*************************************************");
        System.out.println("**************** MENU IMPRESSORA ****************");
        System.out.println("*************************************************");
        System.out.println("1  - Configurar Conexão");
        System.out.println("2  - Abrir Conexão");
        System.out.println("3  - Imprimir QR Code");
        System.out.println("4  - Imprimir Texto");
        System.out.println("5  - Imprimir Código de Barras");
        System.out.println("6  - Imprimir Cupom SAT (XML)");
        System.out.println("7  - Imprimir Cancelamento SAT (XML)");
        System.out.println("8  - Abrir Gaveta");
        System.out.println("9  - Emitir Sinal Sonoro (Bip)");
        System.out.println("10 - Avançar Papel");
        System.out.println("0  - Sair");
        System.out.println("*************************************************\n");
    }

    public static void main(String[] args) {
        if (!fazerLogin()) {
            return;
        }

        int opcao = -1;
        while (opcao != 0) {
            exibirMenu();
            opcao = lerInteiro("Digite a opção desejada: ");

            if (opcao == 1) {
                configurarConexao();
            } else if (opcao == 2) {
                abrirConexao();
            } else if (opcao == 3) {
                imprimirQRCode();
            } else if (opcao == 4) {
                imprimirTexto();
            } else if (opcao == 5) {
                imprimirCodigoBarras();
            } else if (opcao == 6) {
                imprimirXMLSAT();
            } else if (opcao == 7) {
                imprimirXMLCancelamentoSAT();
            } else if (opcao == 8) {
                abrirGaveta();
            } else if (opcao == 9) {
                sinalSonoro();
            } else if (opcao == 10) {
                avancarPapel();
            } else if (opcao == 0) {
                fecharConexao();
                System.out.println("Programa encerrado.");
            } else {
                System.out.println("Opção inválida.");
            }
        }
        scanner.close();
    }
}