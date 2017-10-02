package com.epam.tamasknizner.springadvancedtraining.domain.pdf;

import com.epam.tamasknizner.springadvancedtraining.domain.Ticket;
import com.lowagie.text.Document;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Set;

/**
 * @author Tamas_Knizner
 */
public class PdfTicketView extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> map, Document document, PdfWriter pdfWriter,
        HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        Set<Ticket> tickets = (Set<Ticket>) map.get("ticketsForEvent");
        Table table = new Table(4);
        table.addCell("Ticket ID");
        table.addCell("Guest");
        table.addCell("Event");
        table.addCell("Seat No.");
        for (Ticket ticket : tickets) {
            table.addCell(ticket.getId().toString());
            table.addCell(ticket.getUser().getFirstName() + " " + ticket.getUser().getLastName());
            table.addCell(ticket.getEvent().getName());
            table.addCell(Float.toString(ticket.getSeat()));
        }
        document.add(table);
    }
}
