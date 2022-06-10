package pl.wat.tai.carsharing.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.wat.tai.carsharing.data.requests.ShowroomRequest;
import pl.wat.tai.carsharing.data.response.ShowroomResponse;
import pl.wat.tai.carsharing.services.interfaces.ShowroomService;

import java.util.List;

@RestController
@RequestMapping("/showroom")
@RequiredArgsConstructor
public class ShowroomController {


    private final ShowroomService showroomService;

    @PostMapping("/add")
    public void addShowroom(@RequestBody ShowroomRequest showroomRequest){
        showroomService.addShowroom(showroomRequest);
    }

    @PostMapping("/remove/{name}")
    public void removeShowroom(@PathVariable String name){
        showroomService.removeShowroom(name);
    }

    @GetMapping("/all")
    public List<ShowroomResponse> getAll(){
        return  showroomService.getAll();
    }

    @GetMapping("/{name}")
    public ShowroomResponse get(@PathVariable String name){
        return showroomService.get(name);
    }

}
