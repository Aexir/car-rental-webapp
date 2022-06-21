package pl.wat.tai.carsharing.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.wat.tai.carsharing.data.requests.TableRequest;
import pl.wat.tai.carsharing.data.response.TableResponse;
import pl.wat.tai.carsharing.services.interfaces.TableService;

import java.util.List;

@RestController
@RequestMapping("/table")
@CrossOrigin
@RequiredArgsConstructor
public class TableController {

    private final TableService tableService;

    @PostMapping()
    public List<TableResponse> getAll(@RequestBody TableRequest tableRequest) {
        return tableService.getAll(tableRequest);
    }
}
