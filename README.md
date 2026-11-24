# ControleImpressoraElgin

📘 Sobre o Projeto
Este projeto foi desenvolvido por alunos com o objetivo de criar uma aplicação Java capaz de se comunicar com a impressora Elgin i9, utilizando comandos ESC/POS, testes de impressão e interação via porta de comunicação.
O trabalho faz parte da disciplina de Programação Orientada a Objetos

🧰 Tecnologias Utilizadas
- Java 17
- IntelliJ IDEA
- Biblioteca ESC/POS (JNA)
- Driver/SDK da Elgin i9 (`E1_Impressora01.dll`)
- Maven ou Gradle

📁 Estrutura do Projeto
- `/src` → Código-fonte principal (`Main.java`)
- `/lib` → Bibliotecas e dependências externas (`jna-5.15.0.jar`)
- `/docs` → Materiais adicionais e documentação
- `/examples` → Testes e arquivos de demonstração (XMLs do SAT)

⚙️ Instalação e Execução

🔽 1. Baixar o Projeto do GitHub
1. Acesse o repositório oficial do projeto.
2. Clique no botão verde **Code**.
3. Selecione **Download ZIP**.
4. Aguarde a conclusão do download.

📦 2. Extrair o Arquivo ZIP
1. Vá até o local onde o arquivo compactado foi salvo.
2. Clique com o botão direito sobre o arquivo e escolha **Extrair aqui** / **Extract All**.
3. Será criada uma nova pasta contendo todos os arquivos do projeto.

🧭 3. Importar no IntelliJ IDEA
1. Abra o IntelliJ IDEA.
2. Clique em **Open**.
3. Selecione a pasta extraída do projeto.
4. Aguarde o carregamento do ambiente.
5. Caso apareça o aviso, clique em **Import Project**.
6. Verifique se o SDK está configurado corretamente em:
   - `File` → `Project Structure` → `Project SDK` → `Java 17`.
7. Certifique-se de que o *Working Directory* nas configurações de execução aponta para a raiz do projeto (onde está a DLL).
8. Abra o arquivo `Main.java` e clique no botão ▶️ **Run**.

🧪 Testes
- Teste de conexão com a impressora (porta USB)
- Impressão de texto simples e com múltiplas cópias
- Envio de comandos ESC/POS, QR Code e Código de Barras
- Impressão de cupons fiscais SAT (XML de venda e cancelamento)
- Comandos de hardware (Abertura de gaveta, sinal sonoro e corte de papel)

📚 Documentação Adicional
- Manual da Impressora Térmica Elgin i9
- Documentação oficial do SDK Elgin
- Referência de comandos ESC/POS

👥 Autoress
*(em ordem alfabética)*
- Daniel Alcantara - 251451
- Guilherme dos Santos Silva - 247787
- João Gabriel Mathias - 250744
- Rafael Sander de Azevedo - 248230
- Samuel Carlos Duarte - 007090
-
