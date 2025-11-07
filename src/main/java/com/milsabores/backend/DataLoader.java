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
                                "https://www.elinasaiach.com/wp-content/uploads/2022/04/Mousse-Chocolate-3.jpg"),
                        new Producto (null, "tiramisú Clásico", 5500.0, "https://recetasdecocina.elmundo.es/wp-content/uploads/2022/08/tiramisu-postre-italiano.jpg"),
                        new Producto(null, "Torta sin azúcar de naranja", 48000.0, "https://www.lomismoperosano.cl/wp-content/uploads/2022/01/receta-torta-panqueque-naranja.jpg"),
                        new Producto(null, "Cheesecake sin Azúcar", 47000.0, "https://www.hola.com/horizon/landscape/64c21cd97107-tarta-horno-queso-t.jpg"),
                        new Producto(null, "Empanada de Manzana", 3000.0, "https://cdn7.kiwilimon.com/recetaimagen/1366/960x640/2229.jpg.jpg"),
                        new Producto(null, "Tarta de Santiago", 6000.0, "https://jetextramar.com/wp-content/uploads/2021/11/tarta-de-santiago1-empresa-de-alimentos.jpg"),
                        new Producto(null, "Brownie sin gluten", 4000.0, "https://azucarledesma.com/wp-content/uploads/2024/01/20240131-BrownieLight.jpg"),
                        new Producto(null, "Pan sin Gluten", 3500.0, "https://dinkel.es/wp-content/uploads/2020/06/1041-Pan-sin-Gluten-2.png"),
                        new Producto(null, "Torta vegana de Chocolate", 50000.0, "https://luciapaula.com/wp-content/uploads/2023/01/Blog-1970-01-20-125839033.jpg"),
                        new Producto(null, "Galletas veganas de Avena", 4500.0, "https://i.blogs.es/8792e6/galletas-avea-tahina-datiles/840_560.jpg"),
                        new Producto(null, "Torta especial de Cumpleaños", 55000.0, "https://i.pinimg.com/originals/aa/a0/4f/aaa04fb61005c9fc6d748dee6eac76f3.jpg"),
                        new Producto(null, "Torta especial de Boda", 60000.0, "https://i.pinimg.com/474x/3b/bc/bb/3bbcbb826b865e5278f53a5b2661c2e5.jpg")
                );

                productoRepository.saveAll(iniciales);
                System.out.println("Productos iniciales cargados en la base de datos exitosamente OwO.");
            }
        };
    }
}
