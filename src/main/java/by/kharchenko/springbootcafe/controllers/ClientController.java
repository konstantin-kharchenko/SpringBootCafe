package by.kharchenko.springbootcafe.controllers;

import by.kharchenko.springbootcafe.dto.UserDTO;
import by.kharchenko.springbootcafe.exception.ServiceException;
import by.kharchenko.springbootcafe.mapsrtuct.UserMapper;
import by.kharchenko.springbootcafe.model.Client;
import by.kharchenko.springbootcafe.model.Order;
import by.kharchenko.springbootcafe.model.Product;
import by.kharchenko.springbootcafe.model.User;
import by.kharchenko.springbootcafe.service.ClientService;
import by.kharchenko.springbootcafe.service.OrderService;
import by.kharchenko.springbootcafe.service.ProductService;
import by.kharchenko.springbootcafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import static by.kharchenko.springbootcafe.controllers.PagePath.MAIN_CLIENT_PAGE;
import static by.kharchenko.springbootcafe.controllers.RequestAttribute.FIRST_PAGE;
import static by.kharchenko.springbootcafe.controllers.RequestAttribute.LAST_PAGE;


/*
* This controller for client
*/
@Controller
@RequestMapping("/client")
public class ClientController {
    private final UserService userService;
    private final ClientService clientService;
    private final OrderService orderService;
    private final ProductService productService;

    @Autowired(required = true)
    public ClientController(UserService userService, ClientService clientService, OrderService orderService, ProductService productService) {
        this.userService = userService;
        this.clientService = clientService;
        this.orderService = orderService;
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public String client(@PathVariable("id") BigInteger id, Model model) throws ServletException {
        try {
            User user = userService.findById(id);
            UserDTO userDTO = UserMapper.INSTANCE.userToUserDto(user);
            System.out.print(userDTO.getEmail());
            model.addAttribute("user", user);
            List<Order> orderList = orderService.findQuickToReceive(user);
            model.addAttribute("orders", orderList);
            List<Product> newProducts = productService.findNew();
            model.addAttribute("newProducts", newProducts);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        return MAIN_CLIENT_PAGE;
    }

    @GetMapping("/profile/{id}")
    public String goProfile(@PathVariable("id") BigInteger id, Model model) throws ServletException {
        try {
            User user = userService.findById(id);
            model.addAttribute("user", user);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        return "client/profile";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult result, HttpSession session) throws ServletException {
        try {
            if (result.hasErrors()) {
                return "client/profile";
            }
            boolean isUpdate = userService.update(user);
            if (!isUpdate) {
                session.setAttribute("msg", "This login is already exists");
                return "redirect:/client/profile/" + user.getIdUser();
            }
            return "redirect:/client/" + user.getIdUser();
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }

    @GetMapping("/delete-order-from-main/{id}")
    public String deleteOrderFromMain(@PathVariable("id") BigInteger id, HttpSession session) throws ServletException {
        try {
            orderService.delete(id);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        BigInteger idUser = (BigInteger) session.getAttribute("id_user");
        return "redirect:/client/" + idUser;
    }

    @GetMapping("/delete-order-from-all/{id}")
    public String deleteOrderFromAll(@PathVariable("id") BigInteger id) throws ServletException {
        try {
            orderService.delete(id);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        return "redirect:/client/all-orders/1";
    }

    @PostMapping("/change_photo")
    public String changePhoto(@RequestParam("file") MultipartFile file, HttpSession session) throws ServletException {
        BigInteger id = (BigInteger) session.getAttribute("id_user");
        try {
            boolean isAdd = clientService.updatePhoto(file, id);
            if (!isAdd) {
                session.setAttribute("msg", "invalid photo file");
            }
            return "redirect:/client/profile/" + id;
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }

    @GetMapping("/create-order")
    public String goCreateOrder(@ModelAttribute("order") Order order) {
        return "client/create_order";
    }

    @PostMapping("/create-order")
    public String createOrder(@ModelAttribute("order") @Valid Order order, BindingResult result, Model model, HttpSession session) throws ServletException {
        try {
            if (result.hasErrors()) {
                return "client/create_order";
            }
            BigInteger id = (BigInteger) session.getAttribute("id_user");
            Client client = clientService.findByUserId(id);
            order.setClient(client);
            orderService.add(order);
            return "redirect:/client/" + id;
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }

    @GetMapping("/change-order/{id}")
    public String goChangeOrder(@PathVariable("id") BigInteger id, Model model) throws ServletException {
        try {
            model.addAttribute("order", orderService.findById(id));
            return "client/change_order";
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }


    @GetMapping("/change-order/{id}/{page}")
    public String goChangeOrder(@PathVariable("id") BigInteger id, @PathVariable("page") Integer page, Model model) throws ServletException {
        try {
            model.addAttribute("order", orderService.findById(id));
            model.addAttribute("currentPage", page);
            return "client/change_order";
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }

    @GetMapping("/add-from-basket/{id}")
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

    @PostMapping("/update-order/{id}")
    public String updateOrder(@PathVariable("id") BigInteger id, @ModelAttribute("order") @Valid Order order, BindingResult result, @RequestParam(value = "currentPage", required = false) Integer page, Model model, HttpSession session) throws ServletException {
        try {
            order.setIdOrder(id);
            if (result.hasErrors()) {
                return "client/change_order";
            }
            BigInteger idUser = (BigInteger) session.getAttribute("id_user");
            Client client = clientService.findByUserId(idUser);
            order.setClient(client);
            orderService.update(order);
            if (page != null) {
                return "redirect:/client/all-orders/" + page;
            }
            return "redirect:/client/" + idUser;
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }

    @GetMapping("/delete-from-order/{idOrder}/{idProduct}")
    public String deleteFromOrder(@PathVariable("idOrder") BigInteger idOrder, @PathVariable("idProduct") BigInteger idProduct, HttpSession session) throws ServletException {
        try {
            orderService.deleteProductFromOrderById(idOrder, idProduct);
            BigInteger idUser = (BigInteger) session.getAttribute("id_user");
            return "redirect:/client/" + idUser;
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }

    @GetMapping("/delete-from-order/{idOrder}/{idProduct}/{page}")
    public String deleteFromOrder(@PathVariable("idOrder") BigInteger idOrder, @PathVariable("idProduct") BigInteger idProduct, @PathVariable("page") Integer page, HttpSession session) throws ServletException {
        try {
            orderService.deleteProductFromOrderById(idOrder, idProduct);
            return "redirect:/client/all-orders/" + page;
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }

    @GetMapping("/add-to-basket/{id}")
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

    @GetMapping("/add-to-basket/{id}/{page}")
    public String addToBasket(@PathVariable("id") BigInteger id, @PathVariable("page") Integer page, HttpSession session, Model model) throws ServletException {
        Set<BigInteger> basketProducts = (Set<BigInteger>) session.getAttribute("basketProductsId");
        if (basketProducts == null) {
            basketProducts = new HashSet<>();
        }
        basketProducts.add(id);
        session.setAttribute("basketProductsId", basketProducts);
        Integer pageCount = (int) Math.ceil((1.0 * productService.ordersCount().intValue()) / 10);
        page(model, page, pageCount);
        return "redirect:/client/all-products/" + page;

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

    @GetMapping("/add-client-account")
    public String goClientAccount() {
        return "client/add_account";
    }

    @PostMapping("/add-client-account")
    public String addClientAccount(@RequestParam("account") BigDecimal clientAccount, HttpSession session, Model model) {
        BigInteger idUser = (BigInteger) session.getAttribute("id_user");
        boolean match = clientService.addClientAccount(clientAccount, idUser);
        if (match) {
            return "redirect:/client/" + idUser;
        } else {
            model.addAttribute("msg", "Incorrect Client Account");
            return "client/add_account";
        }

    }

    @GetMapping("/all-orders/{page}")
    public String allOrders(@PathVariable("page") Integer page, Model model) throws ServletException {
        try {
            List<Order> orders = orderService.findByCurrentPage(page);
            Integer pageCount = (int) Math.ceil((1.0 * orderService.ordersCount().intValue()) / 10);
            model.addAttribute("orders", orders);
            page(model, page, pageCount);
            return "client/all_orders";
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }

    @GetMapping("/all-products/{page}")
    public String goAllProducts(@PathVariable("page") Integer page, Model model) throws ServletException {
        try {
            List<Product> products = productService.findByCurrentPage(page);
            Integer pageCount = (int) Math.ceil((1.0 * productService.ordersCount().intValue()) / 10);
            model.addAttribute("products", products);
            page(model, page, pageCount);
            return "client/all_products";
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }

    @GetMapping("/search-product")
    public String searchProduct(@RequestParam(value = "search", required = false) String name, Model model) {
        if (name == null || name.equals("")) {
            return "redirect:/client/all-products/1";
        }
        Product product = productService.findByName(name);
        model.addAttribute("search_product", product);
        page(model, 1, 1);
        return "client/all_products";
    }

    private void page(Model model, Integer page, Integer pageCount) {
        model.addAttribute("currentPage", page);
        if (pageCount == 0) {
            model.addAttribute("pageCount", 1);
        } else {
            model.addAttribute("pageCount", pageCount);
        }
        if (pageCount == 1) {
            model.addAttribute(FIRST_PAGE, true);
            model.addAttribute(LAST_PAGE, true);
        } else if (Objects.equals(page, pageCount)) {
            model.addAttribute(FIRST_PAGE, true);
            model.addAttribute(LAST_PAGE, false);
        } else if (page == 1) {
            model.addAttribute(FIRST_PAGE, true);
            model.addAttribute(LAST_PAGE, false);
        } else {
            model.addAttribute(FIRST_PAGE, false);
            model.addAttribute(LAST_PAGE, false);
        }
    }

    @ModelAttribute
    public void msg(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String msg = (String) session.getAttribute("msg");
        if (msg != null) {
            model.addAttribute("msg", msg);
            session.setAttribute("msg", null);
        }
    }
}
