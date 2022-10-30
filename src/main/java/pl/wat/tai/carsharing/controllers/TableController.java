package pl.wat.tai.carsharing.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.wat.tai.carsharing.data.response.TableResponse;
import pl.wat.tai.carsharing.services.interfaces.TableService;

import java.util.List;

@RestController
@RequestMapping("/table")
@CrossOrigin
@RequiredArgsConstructor
public class TableController {

    private final TableService tableService;

    @GetMapping
    public List<TableResponse> getAll(@RequestParam String name) {
        return tableService.getAll(name);
    }
}
