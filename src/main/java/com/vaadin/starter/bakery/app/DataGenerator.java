package com.vaadin.starter.bakery.app;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.starter.bakery.backend.data.OrderState;
import com.vaadin.starter.bakery.backend.data.Role;
import com.vaadin.starter.bakery.backend.data.entity.Customer;
import com.vaadin.starter.bakery.backend.data.entity.HistoryItem;
import com.vaadin.starter.bakery.backend.data.entity.Order;
import com.vaadin.starter.bakery.backend.data.entity.OrderItem;
import com.vaadin.starter.bakery.backend.data.entity.PickupLocation;
import com.vaadin.starter.bakery.backend.data.entity.Product;
import com.vaadin.starter.bakery.backend.data.entity.User;
import com.vaadin.starter.bakery.backend.repositories.OrderRepository;
import com.vaadin.starter.bakery.backend.repositories.PickupLocationRepository;
import com.vaadin.starter.bakery.backend.repositories.ProductRepository;
import com.vaadin.starter.bakery.backend.repositories.UserRepository;

/**
 * {@code DataGenerator} é responsável por gerar dados de demonstração
 * para a aplicação de pastelaria. 
 *
 * <p>Gera utilizadores, produtos, locais de recolha
 * e encomendas de forma aleatória, populando a base de dados para
 * fins de teste e demonstração.</p>
 *
 * <p>É anotado com {@link SpringComponent}, sendo inicializado 
 * automaticamente pelo Spring.</p>
 */
@SpringComponent
public class DataGenerator implements HasLogger {

    private final Random random = new Random(1L);

    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private PickupLocationRepository pickupLocationRepository;
    private PasswordEncoder passwordEncoder;

    /**
     * Construtor com injeção de dependências.
     *
     * @param orderRepository repositório de encomendas
     * @param userRepository repositório de utilizadores
     * @param productRepository repositório de produtos
     * @param pickupLocationRepository repositório de locais de recolha
     * @param passwordEncoder codificador de passwords
     */
    @Autowired
    public DataGenerator(OrderRepository orderRepository, UserRepository userRepository,
            ProductRepository productRepository, PickupLocationRepository pickupLocationRepository,
            PasswordEncoder passwordEncoder) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.pickupLocationRepository = pickupLocationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Inicializa a base de dados com dados fictícios logo após a construção
     * do componente, caso ainda não existam utilizadores registados.
     */
    @PostConstruct
    public void loadData() {
        if (userRepository.count() != 0L) {
            getLogger().info("Using existing database");
            return;
        }
        getLogger().info("Generating demo data");

        getLogger().info("... generating users");
        User baker = createBaker(userRepository, passwordEncoder);
        User barista = createBarista(userRepository, passwordEncoder);
        createAdmin(userRepository, passwordEncoder);
        createDeletableUsers(userRepository, passwordEncoder);

        getLogger().info("... generating products");
        Supplier<Product> productSupplier = createProducts(productRepository, 8);
        createProducts(productRepository, 4);

        getLogger().info("... generating pickup locations");
        Supplier<PickupLocation> pickupLocationSupplier = createPickupLocations(pickupLocationRepository);

        getLogger().info("... generating orders");
        createOrders(orderRepository, productSupplier, pickupLocationSupplier, barista, baker);

        getLogger().info("Generated demo data");
    }

    /**
     * Preenche um cliente com dados aleatórios (nome, telefone e detalhes).
     *
     * @param customer cliente a preencher
     */
    private void fillCustomer(Customer customer) { ... }

    /**
     * Cria várias encomendas fictícias distribuídas pelos últimos anos,
     * simulando tendências de crescimento.
     *
     * @param orderRepo repositório de encomendas
     * @param productSupplier fornecedor de produtos
     * @param pickupLocationSupplier fornecedor de locais de recolha
     * @param barista utilizador barista
     * @param baker utilizador padeiro
     */
    private void createOrders(OrderRepository orderRepo, Supplier<Product> productSupplier,
            Supplier<PickupLocation> pickupLocationSupplier, User barista, User baker) { ... }

    /**
     * Cria uma encomenda fictícia com produtos, cliente e histórico.
     *
     * @param productSupplier fornecedor de produtos
     * @param pickupLocationSupplier fornecedor de locais de recolha
     * @param barista utilizador barista
     * @param baker utilizador padeiro
     * @param dueDate data prevista da encomenda
     * @return encomenda gerada
     */
    private Order createOrder(Supplier<Product> productSupplier, Supplier<PickupLocation> pickupLocationSupplier,
            User barista, User baker, LocalDate dueDate) { ... }

    /**
     * Cria um histórico de estados para uma encomenda,
     * simulando o ciclo de vida da mesma (novo, confirmado,
     * pronto, entregue ou cancelado).
     *
     * @param order encomenda
     * @param barista utilizador barista
     * @param baker utilizador padeiro
     * @return lista de entradas de histórico
     */
    private List<HistoryItem> createOrderHistory(Order order, User barista, User baker) { ... }

    /**
     * Cria utilizadores padrão da aplicação: padeiro, barista e admin.
     *
     * @param userRepository repositório de utilizadores
     * @param passwordEncoder codificador de passwords
     * @return instâncias {@link User}
     */
    private User createBaker(UserRepository userRepository, PasswordEncoder passwordEncoder) { ... }
    private User createBarista(UserRepository userRepository, PasswordEncoder passwordEncoder) { ... }
    private User createAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) { ... }
}
