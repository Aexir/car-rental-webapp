package pl.wat.tai.carsharing.services.interfaces;

import pl.wat.tai.carsharing.data.response.TableResponse;

import java.util.List;

public interface TableService {
    List<TableResponse> getAll(String name);
}
