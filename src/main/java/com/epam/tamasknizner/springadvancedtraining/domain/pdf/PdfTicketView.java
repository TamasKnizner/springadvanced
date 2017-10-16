package com.epam.tamasknizner.springadvancedtraining.domain.pdf;

import com.epam.tamasknizner.springadvancedtraining.domain.Ticket;
import com.lowagie.text.BadElementException;
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

    private static final String TICKET_ID_HEADER = "Ticket ID";
    private static final String GUEST_HEADER = "Guest";
    private static final String EVENT_HEADER = "Event";
    private static final String SEAT_NO_HEADER = "Seat No.";

    @Override
    protected void buildPdfDocument(Map<String, Object> map, Document document, PdfWriter pdfWriter,
        HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        Set<Ticket> tickets = (Set<Ticket>) map.get("ticketsForEvent");
        Table table = new Table(4);
        addTableHeaders(table);
        for (Ticket ticket : tickets) {
            fillCells(ticket, table);
        }
        document.add(table);
    }

    private void addTableHeaders(Table table) throws BadElementException {
        table.addCell(TICKET_ID_HEADER);
        table.addCell(GUEST_HEADER);
        table.addCell(EVENT_HEADER);
        table.addCell(SEAT_NO_HEADER);
    }

    private void fillCells(final Ticket ticket, final Table table) throws BadElementException {
        table.addCell(ticket.getId().toString());
        table.addCell(ticket.getUser().getFirstName() + " " + ticket.getUser().getLastName());
        table.addCell(ticket.getEvent().getName());
        table.addCell(Float.toString(ticket.getSeat()));
    }
}
