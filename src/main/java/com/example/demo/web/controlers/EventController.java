package com.example.demo.web.controlers;

import com.example.demo.models.bindingmodel.EventCreateBindingModel;
import com.example.demo.models.bindingmodel.EventOrderBindingModel;
import com.example.demo.models.service.EventServiceModel;
import com.example.demo.models.service.MyEventsServiceModel;
import com.example.demo.models.view.AllEventsEventViewModel;
import com.example.demo.models.view.MyEventsEventModel;
import com.example.demo.service.EventService;
import com.example.demo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("events")
public class EventController extends BaseController {

//    private final EventService eventService;
//
//    private final ModelMapper modelMapper;
//
//    private final JmsTemplate jmsTemplate;
//
//    @Autowired
//    public EventController(EventService eventService, ModelMapper modelMapper,
//                           JmsTemplate jmsTemplate) {
//        this.eventService = eventService;
//        this.modelMapper = modelMapper;
//        this.jmsTemplate = jmsTemplate;
//    }
//
//    @GetMapping("/all")
//    public ModelAndView allEvents() {
//        return this.view("events-all");
//    }
//
//    @GetMapping(value = "/api/all", produces = "application/json")
//    @ResponseBody
//    public Set<AllEventsEventViewModel> apiAllEvents() {
//        Set<AllEventsEventViewModel> allEventsViewModel =
//                this.eventService.getAll()
//                        .stream()
//                        .map(x -> {
//                            AllEventsEventViewModel viewModel =
//                                    this.modelMapper.map(x, AllEventsEventViewModel.class);
//                            viewModel.setRemainingTickets(x.getTotalTickets() -
//                                    x.getSoldTickets());
//                            viewModel.setAvailable(x.getStartTime().compareTo(LocalDateTime.now())
//                                    > 0 && viewModel.getRemainingTickets() > 0);
//                            return viewModel;
//
//                        }).collect(Collectors.toSet());
//        return allEventsViewModel;
//    }
//
//    @GetMapping(value = "/api/available", produces = "application/json")
//    @ResponseBody
//    public Set<AllEventsEventViewModel> apiAvailableEvents() {
//        Set<AllEventsEventViewModel> allEventsViewModel =
//                this.eventService
//                        .getUnavailable()
//                        .stream()
//                        .map(x -> {
//                            AllEventsEventViewModel viewModel = this.modelMapper
//                                    .map(x, AllEventsEventViewModel.class);
//                            viewModel.setAvailable(true);
//                            viewModel.setRemainingTickets(x.getTotalTickets() - x.getSoldTickets());
//                            return viewModel;
//                        }).collect(Collectors.toSet());
//
//        return allEventsViewModel;
//
//    }
//
//    @GetMapping(value = "/api/unavailable", produces = "application/json")
//    @ResponseBody
//    public Set<AllEventsEventViewModel> apiUnAvailableEvents() {
//        Set<AllEventsEventViewModel> allEventsViewModel =
//                this.eventService
//                        .getUnavailable()
//                        .stream()
//                        .map(x -> {
//                            AllEventsEventViewModel viewModel = this.modelMapper
//                                    .map(x, AllEventsEventViewModel.class);
//                            viewModel.setAvailable(false);
//                            viewModel.setRemainingTickets(x.getTotalTickets() - x.getSoldTickets());
//                            return viewModel;
//                        }).collect(Collectors.toSet());
//
//        return allEventsViewModel;
//    }
//
//    @GetMapping("/my")
//    public ModelAndView myEvents(Principal principal, ModelAndView modelAndView) {
//        Set<MyEventsEventModel> myEventsViewModel =
//                this.eventService.myEvents(principal.getName())
//                        .stream()
//                        .map(x -> this.modelMapper
//                                .map(x, MyEventsEventModel.class))
//                        .collect(Collectors.toSet());
//        modelAndView.addObject("myEvents", myEventsViewModel);
//
//        return this.view("events-my", modelAndView);
//    }
//
//    @GetMapping("/create")
//    public ModelAndView createEvent() {
//        return this.view("events-create");
//    }
//
//    @PostMapping("/create")
//    public ModelAndView createEventConfirm(@ModelAttribute EventCreateBindingModel
//                                                   eventCreateBindingModel) {
//        boolean result = this.eventService
//                .createEvents(this.modelMapper
//                        .map(eventCreateBindingModel, EventServiceModel.class));
//        if (!result) {
//            throw new IllegalArgumentException("asd");
//        }
//        return this.redirect("all");
//    }
//@PostMapping("/order")
//    public ModelAndView order(@ModelAttribute EventOrderBindingModel eventOrderBindingModel,
//                              Principal principal,ModelAndView modelAndView){
//
//    Map<String,Object>jmsArguments = new HashMap<>(){{
//        put("tickets",eventOrderBindingModel.getTickets());
//        put("eventId",eventOrderBindingModel.getEventId());
//        put("username",principal.getName());
//    }};
//
//    jmsTemplate.setDefaultDestinationName("order-event-listener");
//    jmsTemplate.convertAndSend(jmsArguments);
//
//    return this.redirect("all");
//}
private final EventService eventService;

    private final ModelMapper modelMapper;

    private final JmsTemplate jmsTemplate;

    @Autowired
    public EventController(EventService eventService, UserService userService, ModelMapper modelMapper, JmsTemplate jmsTemplate) {
        this.eventService = eventService;
        this.modelMapper = modelMapper;
        this.jmsTemplate = jmsTemplate;
    }


    @GetMapping("/all")
    public ModelAndView allEvents(ModelAndView modelAndView) {
        return this.view("events-all");
    }

    @GetMapping(value = "/api/all", produces = "application/json")
    @ResponseBody
    public Set<AllEventsEventViewModel> apiAllEvents() {
        Set<AllEventsEventViewModel> allEventsViewModel = this
                .eventService
                .getAll()
                .stream()
                .map(x ->  {
                    AllEventsEventViewModel viewModel = this.modelMapper.map(x, AllEventsEventViewModel.class);

                    viewModel.setRemainingTickets(x.getTotalTickets() - x.getSoldTickets());
                    viewModel.setAvailable(
                            viewModel.getStartTime().compareTo(LocalDateTime.now()) > 0 && viewModel.getRemainingTickets() > 0
                    );

                    return viewModel;
                })
                .collect(Collectors.toUnmodifiableSet());

        return allEventsViewModel;
    }

    @GetMapping(value = "/api/available", produces = "application/json")
    @ResponseBody
    public Set<AllEventsEventViewModel> apiAvailableEvents() {
        Set<AllEventsEventViewModel> allEventsViewModel = this
                .eventService
                .getAvailable()
                .stream()
                .map(x ->  {
                    AllEventsEventViewModel viewModel = this.modelMapper.map(x, AllEventsEventViewModel.class);

                    viewModel.setAvailable(true);
                    viewModel.setRemainingTickets(x.getTotalTickets() - x.getSoldTickets());

                    return viewModel;
                })
                .collect(Collectors.toUnmodifiableSet());

        return allEventsViewModel;
    }

    @GetMapping(value = "/api/unavailable", produces = "application/json")
    @ResponseBody
    public Set<AllEventsEventViewModel> apiUnavailableEvents() {
        Set<AllEventsEventViewModel> allEventsViewModel = this
                .eventService
                .getUnavailable()
                .stream()
                .map(x ->  {
                    AllEventsEventViewModel viewModel = this.modelMapper.map(x, AllEventsEventViewModel.class);

                    viewModel.setAvailable(false);
                    viewModel.setRemainingTickets(x.getTotalTickets() - x.getSoldTickets());

                    return viewModel;
                })
                .collect(Collectors.toUnmodifiableSet());
//
        return allEventsViewModel;
    }

    @GetMapping("/my")
    public ModelAndView myEvents(Principal principal, ModelAndView modelAndView) {
        Set<MyEventsEventModel> myEventsViewModel = this
                .eventService
                .myEvents(principal.getName())
                .stream()
                .map(x -> this.modelMapper.map(x, MyEventsEventModel.class))
                .collect(Collectors.toUnmodifiableSet());

        modelAndView.addObject("myEvents", myEventsViewModel);

        return this.view("events-my", modelAndView);
    }

    @GetMapping("/create")
    public ModelAndView createEvent() {
        return this.view("events-create");
    }

    @PostMapping("/create")
    public ModelAndView createEventConfirm(@ModelAttribute EventCreateBindingModel eventCreateBindingModel) {
        boolean result = this.eventService
                .createEvents(this.modelMapper.map(eventCreateBindingModel, EventServiceModel.class));

        if(!result) {
            throw new IllegalArgumentException("asd");
        }

        return this.redirect("all");
    }

    @PostMapping("/order")
    public ModelAndView order(@ModelAttribute EventOrderBindingModel eventOrderBindingModel, Principal principal, ModelAndView modelAndView) {
//        this.eventService
//                .orderEvent(eventOrderBindingModel.getEventId(), principal.getName(), eventOrderBindingModel.getTickets());

        Map<String, Object> jmsArguments = new HashMap<>() {{
            put("tickets", eventOrderBindingModel.getTickets());
            put("eventId", eventOrderBindingModel.getEventId());
            put("username", principal.getName());
        }};

        jmsTemplate.setDefaultDestinationName("order-event-listener");
        jmsTemplate.convertAndSend(jmsArguments);

        return this.redirect("all");
    }
}
