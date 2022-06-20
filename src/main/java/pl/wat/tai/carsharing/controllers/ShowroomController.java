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
@CrossOrigin
public class ShowroomController {


    private final ShowroomService showroomService;

    @PostMapping("/add")
<<<<<<< HEAD
    public void addShowroom(@RequestBody ShowroomRequest showroomRequest){
=======
    public void addShowroom(@RequestBody ShowroomRequest showroomRequest) {
>>>>>>> 2fc4501 (confirmation mails)
        showroomService.addShowroom(showroomRequest);
    }

    @PostMapping("/remove/{name}")
<<<<<<< HEAD
    public void removeShowroom(@PathVariable String name){
=======
    public void removeShowroom(@PathVariable String name) {
>>>>>>> 2fc4501 (confirmation mails)
        showroomService.removeShowroom(name);
    }

    @GetMapping("/all")
<<<<<<< HEAD
    public List<ShowroomResponse> getAll(){
        return  showroomService.getAll();
    }

    @GetMapping("/{name}")
    public ShowroomResponse get(@PathVariable String name){
=======
    public List<ShowroomResponse> getAll() {
        return showroomService.getAll();
    }

    @GetMapping("/{name}")
    public ShowroomResponse get(@PathVariable String name) {
>>>>>>> 2fc4501 (confirmation mails)
        return showroomService.get(name);
    }

    @PostMapping("/{name}/{id}")
<<<<<<< HEAD
    public void addCarToShowroom(@PathVariable String name, @PathVariable long id){
=======
    public void addCarToShowroom(@PathVariable String name, @PathVariable long id) {
>>>>>>> 2fc4501 (confirmation mails)
        showroomService.addCarToShowroom(name, id);
    }

    @GetMapping("/names")
<<<<<<< HEAD
    public List<String> getShowroomNames(){
=======
    public List<String> getShowroomNames() {
>>>>>>> 2fc4501 (confirmation mails)
        return showroomService.getShowroomNames();
    }

}
