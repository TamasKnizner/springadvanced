package com.epam.tamasknizner.springadvancedtraining.rest.messageconverter;

import com.epam.tamasknizner.springadvancedtraining.domain.Ticket;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TicketMessageConverter implements HttpMessageConverter<Set<Ticket>> {

    @Override
    public boolean canRead(final Class<?> clazz, final MediaType mediaType) {
        return false;
    }

    @Override
    public boolean canWrite(final Class<?> clazz, final MediaType mediaType) {
        return MediaType.APPLICATION_PDF.equals(mediaType) && clazz.isInstance(new HashSet<Ticket>());
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return Collections.singletonList(MediaType.APPLICATION_PDF);
    }

    @Override
    public Set<Ticket> read(final Class<? extends Set<Ticket>> clazz, final HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    @Override
    public void write(final Set<Ticket> tickets, final MediaType contentType, final HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        outputMessage.getHeaders().setContentType(MediaType.APPLICATION_PDF);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();
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
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        outputMessage.getHeaders().setContentLength(byteArrayOutputStream.size());
        byteArrayOutputStream.writeTo(outputMessage.getBody());
        outputMessage.getBody().flush();
    }
}
