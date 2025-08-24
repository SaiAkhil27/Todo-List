package com.example.toDoList.controller;

import com.example.toDoList.dto.TodoDTO;
import com.example.toDoList.entity.Todo;
import com.example.toDoList.service.TodoService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "http://localhost:4200")
public class TodoController {

    private static final Logger logger = LoggerFactory.getLogger(TodoController.class);
    @Autowired
private TodoService todoService;

    @GetMapping("/")
    public String whatever(){

        logger.info("test request called");

        return "this is the string";
    }

    @GetMapping("/all")
    public List<TodoDTO> getAlltodos(){

        logger.info("request for getting all the data");

        return todoService.getAllTodos();
    }

    @GetMapping("{id}")
    public TodoDTO getById(@PathVariable Long id){
        logger.info("request for getting data by id");

        return todoService.getToDoById(id);
    }

    @PostMapping
    public TodoDTO createNewTodo(@RequestBody TodoDTO todoDTO){
        logger.info("request for creating new task");
        return todoService.createNewTodo(todoDTO);
    }

    @PutMapping("/{id}")
    public TodoDTO updateTodo(@PathVariable Long id, @RequestBody TodoDTO todoDTO) {
        logger.info("request for editing the task with id" + id);

        return todoService.updateTheTodo(id, todoDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id) {
        logger.info("request for deleting the task with id" + id);
        todoService.deleteTodo(id);
    }


    @DeleteMapping("/all")
    public void deleetAll(){
        logger.info("Request for deleting all the requests");

        todoService.deletAll();
    }


    @GetMapping("download/pdf")
    public ResponseEntity<byte[]> downloadPDF() {
        logger.info("Got the request for downloading the PDF");
        try {
            List<TodoDTO> tasks = todoService.getAllTodos();
            logger.info("Fetching the tasks");

            Document document = new Document();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, out);

            document.open();

            // 🎯 Make Heading Bigger, Bold, and Centered
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.BLUE);
            Paragraph title = new Paragraph("📋 Todo List", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20); // add space below heading
            document.add(title);

            logger.info("Document created");

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100); // full width
            table.setSpacingBefore(10f);

            // Table Headers
            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
            BaseColor headerColor = new BaseColor(11, 121, 182);

            String[] headers = {"ID", "Title", "Description", "Status"};
            for (String header : headers) {
                PdfPCell hCell = new PdfPCell(new Phrase(header, headFont));
                hCell.setBackgroundColor(headerColor);
                hCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(hCell);
            }

            // Table Rows
            for (TodoDTO task : tasks) {

                PdfPCell idCell = new PdfPCell(new Phrase(String.valueOf(task.getId())));
                idCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(idCell);

                PdfPCell titleCell = new PdfPCell(new Phrase(task.getTitle()));
                titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(titleCell);

                PdfPCell descCell = new PdfPCell(new Phrase(task.getDescription()));
                descCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(descCell);

                PdfPCell statusCell = new PdfPCell(new Phrase(task.isCompleted() ? "✔️ Completed" : "❌ NO"));
                statusCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(statusCell);
            }

            logger.info("Saved the values in the table");

            document.add(table);
            document.close();

            String today = java.time.LocalDate.now().toString(); // e.g. 2025-08-24
            String day = java.time.LocalDate.now().getDayOfWeek().toString(); // e.g. SUNDAY
            String filename = "todos_" + today + "_" + day + ".pdf";


            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=" + filename)
                    .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                    .body(out.toByteArray());

        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }



}