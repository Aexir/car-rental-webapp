package pl.wat.tai.carsharing.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    public List<TableResponse> getAll(@RequestBody TableRequest tableRequest){
        return tableService.getAll(tableRequest);
    }
}
