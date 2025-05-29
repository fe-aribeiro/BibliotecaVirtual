package br.com.desafio.livraria.service;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.grp;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.desafio.livraria.dto.Response.LivroPorAutorViewDto;
import br.com.desafio.livraria.exception.RelatorioException;
import br.com.desafio.livraria.repository.LivroPorAutorViewRepository;
import br.com.desafio.livraria.util.MensagemUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.column.ValueColumnBuilder;
import net.sf.dynamicreports.report.builder.group.ColumnGroupBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
@Slf4j
public class RelatorioService {

    @Autowired
    private LivroPorAutorViewRepository repository;
    
    @Autowired
	private MensagemUtil mensagemUtil;
    
    public byte[] gerarRelatorioAgrupadoPorAutor() throws Exception {
    	
    	try {
    		
    		List<LivroPorAutorViewDto> dados = repository.buscarRelatorioPorAutor();
    		
            StyleBuilder boldStyle = stl.style().bold();
            StyleBuilder titleStyle = stl.style(boldStyle)
                    .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                    .setFontSize(14)
                    .setBottomPadding(10);
            
            StyleBuilder columnHeaderStyle = stl.style(boldStyle)
                    .setBorder(stl.pen1Point())
                    .setBackgroundColor(Color.LIGHT_GRAY)
                    .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
            
            StyleBuilder centerAligned = stl.style().setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);

            ValueColumnBuilder<?, String> nomeAutorColuna = Columns.column("Autor", "nomeAutor", type.stringType());
            ColumnGroupBuilder autorGroup = grp.group(nomeAutorColuna).setStyle(boldStyle);

            JasperReportBuilder report = report()
                    .columns(
//                    	Columns.column("Autor", "nomeAutor", type.stringType()),
                        Columns.column("Título", "titulo", type.stringType()),
                        Columns.column("Editora", "editora", type.stringType()),
                        Columns.column("Edição", "edicao", type.integerType()).setStyle(centerAligned),
                        Columns.column("Ano", "anoPublicacao", type.stringType()).setStyle(centerAligned),
                        Columns.column("Valor", "valor", type.stringType()),
                        Columns.column("Assunto", "assuntos", type.stringType())
                    )
                    .groupBy(autorGroup)
                    .setColumnTitleStyle(columnHeaderStyle)
                    .title(cmp.text("Relatório de Livros por Autor").setStyle(titleStyle))
                    .highlightDetailEvenRows()
                    .pageFooter(
                            cmp.pageXofY(),
                            cmp.text("Emitido em: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                                .setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT)
                                .setStyle(stl.style().italic())
                        )

                    .setDataSource(new JRBeanCollectionDataSource(dados));

            ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
            report.toPdf(pdfOutputStream);
            return pdfOutputStream.toByteArray();
        } catch (Exception e) {
        	log.error(mensagemUtil.get("erro.relatorio") + ": " + e.getMessage(), e);
            throw new RelatorioException(mensagemUtil.get("erro.relatorio"), e.getCause());
        }
    }
}
