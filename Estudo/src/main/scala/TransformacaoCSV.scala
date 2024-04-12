import java.nio.file.{Files, Paths}
import scala.io.Source
import scala.jdk.CollectionConverters.*

object TransformacaoCSV { // Define a classe TransformacaoCSV como objeto

  def main(args: Array[String]): Unit = { // Define o ponto de entrada da aplicação

    val dados = carregarDados("C:\\Users\\kaikm\\Documents\\GitHub\\Scala_Study\\Estudo\\valores.csv") // Carrega os dados do CSV

    val dadosFiltrados = dados.filter(d => d.investimento >= 100000 && d.ano > 2022) // Filtra dados por investimento >= 100.000 e ano > 2022

    val dadosMapeados = dadosFiltrados.map(d => (d.empresa, d.pais)) // Mapeia dados para (empresa, país)

    criarArquivoCSV("valores-saida.csv", dadosMapeados) // Cria um novo arquivo CSV com os dados mapeados

  }

  def carregarDados(caminhoArquivo: String): Seq[Dados] = { // Função para carregar dados do CSV

    val linhas = Source.fromFile(caminhoArquivo).getLines().toList // Lê o arquivo linha por linha

    linhas.tail.map { linha => // Itera pelas linhas, ignorando a primeira (cabeçalho)
      val colunas = linha.split(",") // Divide a linha em colunas por vírgula
      Dados(colunas(0), colunas(1), colunas(2).toDouble, colunas(3).toInt) // Cria um objeto Dados com os valores das colunas
    }

  }

  def criarArquivoCSV(caminhoArquivo: String, dados: Seq[(String, String)]) : Unit = { // Função para criar um novo arquivo CSV

    val linhas = dados.map { case (empresa, pais) => // Mapeia cada par (empresa, país) para uma string no formato CSV
      s"$empresa,$pais"
    }

    Files.write(Paths.get(caminhoArquivo), linhas.asJava) // Escreve as linhas no arquivo especificado

  }

}
