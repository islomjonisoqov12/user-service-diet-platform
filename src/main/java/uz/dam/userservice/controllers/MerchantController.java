package uz.dam.userservice.controllers;

import org.springframework.stereotype.Controller;
import uz.dam.userservice.services.merchant.MerchantService;

@Controller
public class MerchantController extends AbstractController<MerchantService>{

    protected MerchantController(MerchantService service) {
        super(service);
    }
}
