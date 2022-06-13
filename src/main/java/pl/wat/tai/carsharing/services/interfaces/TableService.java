package pl.wat.tai.carsharing.services.interfaces;

import pl.wat.tai.carsharing.data.requests.TableRequest;
import pl.wat.tai.carsharing.data.response.TableResponse;

import java.util.List;

public interface TableService {
    List<TableResponse> getAll(TableRequest tableRequest);
}
