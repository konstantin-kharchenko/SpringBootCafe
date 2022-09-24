package by.kharchenko.springbootcafe.controllers;

import by.kharchenko.springbootcafe.exception.ServiceException;
import by.kharchenko.springbootcafe.model.Product;
import by.kharchenko.springbootcafe.service.ClientService;
import by.kharchenko.springbootcafe.service.OrderService;
import by.kharchenko.springbootcafe.service.ProductService;
import by.kharchenko.springbootcafe.service.UserService;
import by.kharchenko.springbootcafe.util.count_page_saver.CountPageSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/basket")
public class BasketController {
    private final UserService userService;
    private final ClientService clientService;
    private final OrderService orderService;
    private final ProductService productService;

    @Autowired(required = true)
    public BasketController(UserService userService, ClientService clientService, OrderService orderService, ProductService productService) {
        this.userService = userService;
        this.clientService = clientService;
        this.orderService = orderService;
        this.productService = productService;
    }
    @GetMapping("/{id}/add-to-basket")
    public String addToBasket(@PathVariable("id") BigInteger id, HttpSession session) throws ServletException {
        Set<BigInteger> basketProducts = (Set<BigInteger>) session.getAttribute("basketProductsId");
        if (basketProducts == null) {
            basketProducts = new HashSet<>();
        }
        basketProducts.add(id);
        session.setAttribute("basketProductsId", basketProducts);
        BigInteger idUser = (BigInteger) session.getAttribute("id_user");
        return "redirect:/client/" + idUser;

    }

    @GetMapping("/{id}/add-to-basket/{page}")
    public String addToBasket(@PathVariable("id") BigInteger id, @PathVariable("page") Integer page, HttpSession session, Model model) throws ServletException {
        Set<BigInteger> basketProducts = (Set<BigInteger>) session.getAttribute("basketProductsId");
        if (basketProducts == null) {
            basketProducts = new HashSet<>();
        }
        basketProducts.add(id);
        session.setAttribute("basketProductsId", basketProducts);
        Integer pageCount = (int) Math.ceil((1.0 * productService.ordersCount().intValue()) / 10);
        CountPageSaver.page(model, page, pageCount);
        return "redirect:/client/all-products/" + page;

    }

    @GetMapping("/{id}/add-from-basket")
    public String addFromBasket(@PathVariable("id") BigInteger id, HttpSession session) throws ServletException {
        try {
            Set<BigInteger> basketProducts = (Set<BigInteger>) session.getAttribute("basketProductsId");
            BigInteger idUser = (BigInteger) session.getAttribute("id_user");
            if (basketProducts == null) {
                return "redirect:/client/" + idUser;
            }
            orderService.addProductsInOrder(id, basketProducts);
            session.setAttribute("basketProductsId", null);
            return "redirect:/client/" + idUser;
        } catch (ServiceException e) {
            throw new ServletException(e);
        }

    }

    @GetMapping("/add-from-basket/{id}/{page}")
    public String addFromBasket(@PathVariable("id") BigInteger id, @PathVariable("page") Integer page, HttpSession session) throws ServletException {
        try {
            Set<BigInteger> basketProducts = (Set<BigInteger>) session.getAttribute("basketProductsId");
            if (basketProducts == null) {
                return "redirect:/client/all-orders/" + page;
            }
            orderService.addProductsInOrder(id, basketProducts);
            session.setAttribute("basketProductsId", null);
            return "redirect:/client/all-orders/" + page;
        } catch (ServiceException e) {
            throw new ServletException(e);
        }

    }

    @GetMapping("/basket")
    public String goBasket(Model model, HttpSession session) throws ServletException {
        try {
            Set<BigInteger> basketProducts = (Set<BigInteger>) session.getAttribute("basketProductsId");
            if (basketProducts == null || basketProducts.size() == 0) {
                model.addAttribute("basketProducts", new ArrayList<Product>());
                return "client/basket";
            }
            List<Product> products = productService.findProductsByIdList(basketProducts.stream().toList());
            model.addAttribute("basketProducts", products);
            return "client/basket";
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/delete-from-basket/{id}")
    public String deleteFromBasket(@PathVariable("id") BigInteger id, HttpSession session) throws ServletException {
        Set<BigInteger> basketProducts = (Set<BigInteger>) session.getAttribute("basketProductsId");
        basketProducts.remove(id);
        session.setAttribute("basketProductsId", basketProducts);
        return "redirect:/client/basket";
    }
}
