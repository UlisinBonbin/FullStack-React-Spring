package com.milsabores.backend;

import com.milsabores.backend.model.Producto;
import com.milsabores.backend.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner initDatabase(ProductoRepository productoRepository) {
        return args -> {
            if (productoRepository.count() == 0) {
                List<Producto> iniciales = List.of(
                        //Faltan agregar, cambiar el nombre o precio para que calce con el que teniamos
                        new Producto(null, "Torta de Chocolate", 18990.0,
                                "https://tortasdelacasa.com/wp-content/uploads/2024/02/DSC4340-scaled.jpg"),
                        new Producto(null, "Tarta de Frutas", 15990.0,
                                "https://images.aws.nestle.recipes/original/2024_10_23T06_40_18_badun_images.badun.es_tarta_fria_de_chocolate_blanco_con_frutas.jpg"),
                        new Producto(null, "Torta de Vainilla", 13990.0,
                                "https://tortamaniaecuador.com/wp-content/uploads/2022/12/Vainilla-con-crema-pequena-300x300.png"),
                        new Producto(null, "Torta Ciruela Manjar", 14990.0,
                                "https://rhenania.cl/wp-content/uploads/2020/12/CIRUELA-MANJAR-BLANCO.jpg"),
                        new Producto(null, "Mousse de Chocolate", 16990.0,
                                "https://www.elinasaiach.com/wp-content/uploads/2022/04/Mousse-Chocolate-3.jpg")
                );

                productoRepository.saveAll(iniciales);
                System.out.println("Productos iniciales cargados en la base de datos exitosamente OwO.");
            }
        };
    }
}
