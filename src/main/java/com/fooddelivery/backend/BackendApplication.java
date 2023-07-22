package com.fooddelivery.backend;

import java.time.LocalDate;
import java.time.Month;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fooddelivery.backend.Models.Courier;
import com.fooddelivery.backend.Models.Dish;
import com.fooddelivery.backend.Models.Owner;
import com.fooddelivery.backend.Models.Restaurant;
import com.fooddelivery.backend.Models.Users;
import com.fooddelivery.backend.Models.Enums.CourierStatus;
import com.fooddelivery.backend.Models.Enums.NavigationMode;
import com.fooddelivery.backend.Models.Enums.Role;
import com.fooddelivery.backend.Repository.CourierRepos;
import com.fooddelivery.backend.Repository.CustomerRepos;
import com.fooddelivery.backend.Repository.DishRepos;
import com.fooddelivery.backend.Repository.OwnerRepos;
import com.fooddelivery.backend.Repository.RestaurantRepos;
import com.fooddelivery.backend.Repository.UserRepos;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(UserRepos userRepos, OwnerRepos ownerRepos, CustomerRepos customerRepos, CourierRepos courierRepos, RestaurantRepos restaurantRepos, DishRepos dishRepos) {
		return args -> {
			Users quan = new Users("quan", new BCryptPasswordEncoder().encode("123456"), "quan", "doan", 64.9942532, 25.4618881);
			quan.getRoles().add(Role.USER);
			Users quan2 = new Users("quan2", new BCryptPasswordEncoder().encode("123456"), "quan2", "doan", 64.9942532, 25.4618881);
			quan2.getRoles().add(Role.USER);
			Users khanh = new Users("khanh", new BCryptPasswordEncoder().encode("123456"), "khanh", "nguyen",   65.0540746, 25.4554205);
			khanh.getRoles().add(Role.USER);
			Users khanh2 = new Users("khanh2", new BCryptPasswordEncoder().encode("123456"), "khanh2", "nguyen",   65.0540746, 25.4554205);
			khanh2.getRoles().add(Role.USER);
			Users duy = new Users("duy", new BCryptPasswordEncoder().encode("123456"), "duy", "doan",  65.0104222, 25.4905775);
			duy.getRoles().add(Role.USER);
			Users duy2 = new Users("duy2", new BCryptPasswordEncoder().encode("123456"), "duy2", "doan",  65.0104222, 25.4905775);
			duy2.getRoles().add(Role.USER);
			Users bo = new Users("bo", new BCryptPasswordEncoder().encode("123456"), "bo", "doan",   64.9167, 25.5);
			bo.getRoles().add(Role.USER);
			Users bo2 = new Users("bo2", new BCryptPasswordEncoder().encode("123456"), "bo2", "doan",   64.9167, 25.5);
			bo2.getRoles().add(Role.USER);
			Users my = new Users("my", new BCryptPasswordEncoder().encode("123456"), "my", "nguyen",   60.2646166, 25.0862993);
			my.getRoles().add(Role.USER);
			Users my2 = new Users("my2", new BCryptPasswordEncoder().encode("123456"), "my2", "nguyen",   60.2646166, 25.0862993);
			my2.getRoles().add(Role.USER);
			Users hairj = new Users("hairj", new BCryptPasswordEncoder().encode("123456"), "hairj", "mohammed",   65.011121, 25.4734533);
			hairj.getRoles().add(Role.USER);
			Users hairj2 = new Users("hairj2", new BCryptPasswordEncoder().encode("123456"), "hairj2", "mohammed",   65.011121, 25.4734533);
			hairj2.getRoles().add(Role.USER);
			userRepos.save(quan);
			userRepos.save(quan2);
			userRepos.save(khanh);
			userRepos.save(khanh2);
			userRepos.save(duy);
			userRepos.save(bo);
			userRepos.save(my);
			userRepos.save(hairj);
			userRepos.save(hairj2);
			userRepos.save(duy2);
			userRepos.save(bo2);
			userRepos.save(my2);

			Users quan3 = new Users("quan3", new BCryptPasswordEncoder().encode("123456"), "quan3", "doan",   65.011121, 25.4734533);
			quan3.getRoles().add(Role.USER);
			userRepos.save(quan3);

			Courier courier1 = new Courier(hairj, NavigationMode.CAR);
			courier1.setStatus(CourierStatus.ONLINE);
			courier1.setAvailable(true);
			Courier courier2 = new Courier(my, NavigationMode.BICYCLE);
			courier2.setStatus(CourierStatus.ONLINE);
			courier2.setAvailable(true);
			courierRepos.save(courier1);
			courierRepos.save(courier2);

			Owner owner1 = new Owner(khanh);
			Owner owner2 = new Owner(duy);
			Owner owner3 = new Owner(bo);
			Owner owner4 = new Owner(my);
			Owner owner5 = new Owner(quan);
			Owner owner6 = new Owner(quan2);
			Owner owner7 = new Owner(hairj);
			Owner owner8 = new Owner(hairj2);
			ownerRepos.save(owner1);
			ownerRepos.save(owner2);
			ownerRepos.save(owner3);
			ownerRepos.save(owner4);
			ownerRepos.save(owner5);
			ownerRepos.save(owner6);
			ownerRepos.save(owner7);
			ownerRepos.save(owner8);

			Restaurant puremaku = new Restaurant("pure maku", "Pakkahuoneenkatu 5 B22", "90100", "oulu", "https://notjustdev-dummy.s3.us-east-2.amazonaws.com/uber-eats/restaurant1.jpeg", owner1, 65.0124023, 25.4723617, 3.00);
			puremaku.setCookingTime(20);
			restaurantRepos.save(puremaku);

			Restaurant nekosama = new Restaurant("nekosama", "Kauppakortteli Pekuri, Kirkkokatu 16", "90100", "oulu", "https://notjustdev-dummy.s3.us-east-2.amazonaws.com/uber-eats/restaurant2.jpeg", owner2, 65.0147, 25.4962, 4.00);
			nekosama.setCookingTime(30);
			restaurantRepos.save(nekosama);

			Restaurant  kaupuri5 = new Restaurant("kaupuri5", "Kauppurienkatu 5", "90100", "oulu", "https://notjustdev-dummy.s3.us-east-2.amazonaws.com/uber-eats/restaurant3.jpeg", owner3, 65.0128871, 25.466887, 4.00);
			kaupuri5.setCookingTime(10);
			restaurantRepos.save(kaupuri5);

			Restaurant burgerKing = new Restaurant("burgerKing", "Ritaharjuntie 49", "90540", "oulu", "https://notjustdev-dummy.s3.us-east-2.amazonaws.com/uber-eats/restaurant3.jpeg", owner4, 65.0775092, 25.4475889, 3.50);
			burgerKing.setCookingTime(10);
			restaurantRepos.save(burgerKing);

			Restaurant grahamMasala = new Restaurant("grahamMasala", "Kajaaninkatu 27", "90100", "oulu", "https://notjustdev-dummy.s3.us-east-2.amazonaws.com/uber-eats/restaurant3.jpeg", owner5, 65.0122502, 25.4839302, 3.50);
			grahamMasala.setCookingTime(10);
			restaurantRepos.save(grahamMasala);

			Restaurant lekker61 = new Restaurant("lekker61", " Isokatu 61", "90100", "oulu", "https://notjustdev-dummy.s3.us-east-2.amazonaws.com/uber-eats/restaurant3.jpeg", owner6, 65.01212310791016, 25.473371505737305, 3.50);
			lekker61.setCookingTime(20);
			restaurantRepos.save(lekker61);

			Restaurant istabulOriental = new Restaurant("istabulOriental", " Kauppurienkatu 11", "90100", "oulu", "https://notjustdev-dummy.s3.us-east-2.amazonaws.com/uber-eats/restaurant3.jpeg", owner7, 65.01212310791016, 25.473371505737305, 3.50);
			istabulOriental.setCookingTime(20);
			restaurantRepos.save(istabulOriental);

			Restaurant rolls = new Restaurant("rolls", "Jatkotie 1", "90500", "oulu", "https://notjustdev-dummy.s3.us-east-2.amazonaws.com/uber-eats/restaurant3.jpeg", owner8, 65.01212310791016, 25.473371505737305, 3.50);
			rolls.setCookingTime(20);
			restaurantRepos.save(rolls);


			


			Dish dish1 = new Dish("ramen noodle", "ramen noodles is good", 11, puremaku);
			dishRepos.save(dish1);
			Dish dish2 = new Dish("soyja ramen", "ramen noodles is good", 12, puremaku);
			dishRepos.save(dish2);
			Dish dish3 = new Dish("dark soyja ramen", "ramen noodles is good", 12, puremaku);
			dishRepos.save(dish3);
			Dish dish4 = new Dish("spicy ramen", "ramen noodles 4 is good", 12, puremaku);
			dishRepos.save(dish4);
			Dish dish5 = new Dish("ramen noodle", "ramen noodles 5 is good", 11, nekosama);
			dishRepos.save(dish5);
			Dish dish6 = new Dish("soyja ramen", "ramen noodles 6 is good", 12, nekosama);
			dishRepos.save(dish6);
			Dish dish7 = new Dish("dark soyja ramen", "ramen noodles 7 is good", 12, nekosama);
			dishRepos.save(dish7);
			Dish dish8 = new Dish("spicy ramen", "ramen noodles 8 is good", 12, nekosama);
			dishRepos.save(dish8);
			Dish hamburger1 = new Dish("hamburger1", "hamburger1 is good", 11, kaupuri5);
			dishRepos.save(hamburger1);
			Dish hamburger2 = new Dish("hamburger2", "hamburger2 is good", 12, kaupuri5);
			dishRepos.save(hamburger2);
			Dish hamburger3 = new Dish("hamburger3", "hamburger3 is good", 12, kaupuri5);
			dishRepos.save(hamburger3);
			Dish hamburger4 = new Dish("hamburger4", "hamburger3 is good", 12, kaupuri5);
			dishRepos.save(hamburger4);
			Dish hamburger5 = new Dish("hamburger5", "hamburger5 is good", 11, burgerKing);
			dishRepos.save(hamburger5);
			Dish hamburger6 = new Dish("hamburger6", "hamburger6 is good", 12, burgerKing);
			dishRepos.save(hamburger6);
			Dish hamburger7 = new Dish("hamburger7", "hamburger7 is good", 12, burgerKing);
			dishRepos.save(hamburger7);
			Dish hamburger8 = new Dish("hamburger8", "hamburger8 is good", 12, burgerKing);
			dishRepos.save(hamburger8);
			Dish indianfood5 = new Dish("indianfood5", "indianfood5 is good", 11, grahamMasala);
			dishRepos.save(indianfood5);
			Dish indianfood6 = new Dish("indianfood6", "indianfood6 is good", 12, grahamMasala);
			dishRepos.save(indianfood6);
			Dish indianfood7 = new Dish("indianfood7", "indianfood7 is good", 12, grahamMasala);
			dishRepos.save(indianfood7);
			Dish indianfood8 = new Dish("indianfood8", "indianfood8 is good", 12, grahamMasala);
			dishRepos.save(indianfood8);
			Dish turkishfood5 = new Dish("turkishfood5", "turkishfood5 is good", 11, istabulOriental);
			dishRepos.save(turkishfood5);
			Dish turkishfood6 = new Dish("turkishfood6", "turkishfood6 is good", 12, istabulOriental);
			dishRepos.save(turkishfood6);
			Dish turkishfood7 = new Dish("turkishfood7", "turkishfood7 is good", 12, istabulOriental);
			dishRepos.save(turkishfood7);
			Dish turkishfood8 = new Dish("turkishfood8", "turkishfood8 is good", 12, istabulOriental);
			dishRepos.save(turkishfood8);
			Dish rollingFood5 = new Dish("rollingFood5", "rollingFood5 is good", 11, rolls);
			dishRepos.save(rollingFood5);
			Dish rollingFood6 = new Dish("rollingFood6", "rollingFood6 is good", 12, rolls);
			dishRepos.save(rollingFood6);
			Dish rollingFood7 = new Dish("rollingFood7", "rollingFood7 is good", 12, rolls);
			dishRepos.save(rollingFood7);
			Dish rollingFood8 = new Dish("rollingFood8", "rollingFood8 is good", 12, rolls);
			dishRepos.save(rollingFood8);
			Dish lekkeriFood5 = new Dish("lekkeriFood5", "lekkeriFood5 is good", 11, lekker61);
			dishRepos.save(lekkeriFood5);
			Dish lekkeriFood6 = new Dish("lekkeriFood6", "lekkeriFood6 is good", 12, lekker61);
			dishRepos.save(lekkeriFood6);
			Dish lekkeriFood7 = new Dish("lekkeriFood7", "lekkeriFood7 is good", 12, lekker61);
			dishRepos.save(lekkeriFood7);
			Dish lekkeriFood8 = new Dish("lekkeriFood8", "lekkeriFood8 is good", 12, lekker61);
			dishRepos.save(lekkeriFood8);
			
		};
	}

}
