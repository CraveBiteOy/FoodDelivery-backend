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
			Users hajri = new Users("hajri", new BCryptPasswordEncoder().encode("123456"), "hajri", "mohammed",   65.011121, 25.4734533);
			hajri.getRoles().add(Role.USER);
			Users hajri2 = new Users("hajri2", new BCryptPasswordEncoder().encode("123456"), "hajri2", "mohammed",   65.011121, 25.4734533);
			hajri2.getRoles().add(Role.USER);
			userRepos.save(quan);
			userRepos.save(quan2);
			userRepos.save(khanh);
			userRepos.save(khanh2);
			userRepos.save(duy);
			userRepos.save(bo);
			userRepos.save(my);
			userRepos.save(hajri);
			userRepos.save(hajri2);
			userRepos.save(duy2);
			userRepos.save(bo2);
			userRepos.save(my2);

			Users quan3 = new Users("quan3", new BCryptPasswordEncoder().encode("123456"), "quan3", "doan",   65.011121, 25.4734533);
			quan3.getRoles().add(Role.USER);
			userRepos.save(quan3);

			Courier courier1 = new Courier(hajri, NavigationMode.CAR);
			courier1.setStatus(CourierStatus.ONLINE);
			courier1.setAvailable(true);

			Courier courier2 = new Courier(my, NavigationMode.BICYCLE);
			courier2.setStatus(CourierStatus.ONLINE);
			courier2.setAvailable(true);

			Courier courier3 = new Courier(duy, NavigationMode.BICYCLE);
			courier3.setStatus(CourierStatus.ONLINE);
			courier3.setAvailable(true);

			courierRepos.save(courier1);
			courierRepos.save(courier2);
			courierRepos.save(courier3);

			Owner owner1 = new Owner(khanh);
			Owner owner2 = new Owner(duy);
			Owner owner3 = new Owner(bo);
			Owner owner4 = new Owner(my);
			Owner owner5 = new Owner(quan);
			Owner owner6 = new Owner(quan2);
			Owner owner7 = new Owner(hajri);
			Owner owner8 = new Owner(hajri2);
			ownerRepos.save(owner1);
			ownerRepos.save(owner2);
			ownerRepos.save(owner3);
			ownerRepos.save(owner4);
			ownerRepos.save(owner5);
			ownerRepos.save(owner6);
			ownerRepos.save(owner7);
			ownerRepos.save(owner8);

			Restaurant puremaku = new Restaurant("pure maku", "Pakkahuoneenkatu 5 B22", "90100", "oulu", "https://images.unsplash.com/photo-1493857671505-72967e2e2760?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80", owner1, 65.0124023, 25.4723617, 3.00);
			puremaku.setCookingTime(20);
			restaurantRepos.save(puremaku);

			Restaurant nekosama = new Restaurant("nekosama", "Kauppakortteli Pekuri, Kirkkokatu 16", "90100", "oulu", "https://images.unsplash.com/photo-1511081692775-05d0f180a065?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=386&q=80", owner2, 65.0147, 25.4962, 4.00);
			nekosama.setCookingTime(30);
			restaurantRepos.save(nekosama);

			Restaurant  kaupuri5 = new Restaurant("kaupuri5", "Kauppurienkatu 5", "90100", "oulu", "https://images.unsplash.com/photo-1474898856510-884a2c0be546?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=774&q=80", owner3, 65.0128871, 25.466887, 4.00);
			kaupuri5.setCookingTime(10);
			restaurantRepos.save(kaupuri5);

			Restaurant burgerKing = new Restaurant("burgerKing", "Ritaharjuntie 49", "90540", "oulu", "https://images.unsplash.com/photo-1590660105340-840e6929412e?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80", owner4, 65.0775092, 25.4475889, 3.50);
			burgerKing.setCookingTime(10);
			restaurantRepos.save(burgerKing);

			Restaurant grahamMasala = new Restaurant("grahamMasala", "Kajaaninkatu 27", "90100", "oulu", "https://images.unsplash.com/photo-1582359324766-9c2e09d83d43?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80", owner5, 65.0122502, 25.4839302, 3.50);
			grahamMasala.setCookingTime(10);
			restaurantRepos.save(grahamMasala);

			Restaurant lekker61 = new Restaurant("lekker61", " Isokatu 61", "90100", "oulu", "https://images.unsplash.com/photo-1543007631-283050bb3e8c?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=774&q=80", owner6, 65.01212310791016, 25.473371505737305, 3.50);
			lekker61.setCookingTime(20);
			restaurantRepos.save(lekker61);

			Restaurant istabulOriental = new Restaurant("istabulOriental", " Kauppurienkatu 11", "90100", "oulu", "https://images.unsplash.com/photo-1604025042047-749639ab8a14?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=435&q=80", owner7, 65.01212310791016, 25.473371505737305, 3.50);
			istabulOriental.setCookingTime(20);
			restaurantRepos.save(istabulOriental);

			Restaurant rolls = new Restaurant("rolls", "Jatkotie 1", "90500", "oulu", "https://images.unsplash.com/photo-1531973968078-9bb02785f13d?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=785&q=80", owner8, 65.01212310791016, 25.473371505737305, 3.50);
			rolls.setCookingTime(20);
			restaurantRepos.save(rolls);


			


			Dish dish1 = new Dish("ramen noodle", "ramen noodles is good", 11, "https://images.unsplash.com/photo-1623341214825-9f4f963727da?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80", puremaku);
			dishRepos.save(dish1);
			Dish dish2 = new Dish("soyja ramen", "ramen noodles is good", 12, "https://images.unsplash.com/photo-1526318896980-cf78c088247c?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=387&q=80", puremaku);
			dishRepos.save(dish2);
			Dish dish3 = new Dish("dark soyja ramen", "ramen noodles is good", 12, "https://images.unsplash.com/photo-1591814468924-caf88d1232e1?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80", puremaku);
			dishRepos.save(dish3);
			Dish dish4 = new Dish("spicy ramen", "ramen noodles 4 is good", 12, "https://images.unsplash.com/photo-1618889482923-38250401a84e?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80", puremaku);
			dishRepos.save(dish4);
			Dish dish5 = new Dish("ramen noodle", "ramen noodles 5 is good", 11, "https://images.unsplash.com/photo-1617421753170-46511a8d73fc?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=465&q=80", nekosama);
			dishRepos.save(dish5);
			Dish dish6 = new Dish("soyja ramen", "ramen noodles 6 is good", 12, "https://images.unsplash.com/photo-1602273660127-a0000560a4c1?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=436&q=80", nekosama);
			dishRepos.save(dish6);
			Dish dish7 = new Dish("dark soyja ramen", "ramen noodles 7 is good", 12, "https://images.unsplash.com/photo-1617421753170-46511a8d73fc?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=465&q=80", nekosama);
			dishRepos.save(dish7);
			Dish dish8 = new Dish("spicy ramen", "ramen noodles 8 is good", 12, "https://images.unsplash.com/photo-1552611052-33e04de081de?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=464&q=80", nekosama);
			dishRepos.save(dish8);
			Dish hamburger1 = new Dish("hamburger1", "hamburger1 is good", 11, "https://images.unsplash.com/photo-1594212699903-ec8a3eca50f5?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1171&q=80", kaupuri5);
			dishRepos.save(hamburger1);
			Dish hamburger2 = new Dish("hamburger2", "hamburger2 is good", 12, "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=699&q=80", kaupuri5);
			dishRepos.save(hamburger2);
			Dish hamburger3 = new Dish("hamburger3", "hamburger3 is good", 12, "https://images.unsplash.com/photo-1572802419224-296b0aeee0d9?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1115&q=80", kaupuri5);
			dishRepos.save(hamburger3);
			Dish hamburger4 = new Dish("hamburger4", "hamburger3 is good", 12, "https://images.unsplash.com/photo-1550317138-10000687a72b?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1220&q=80", kaupuri5);
			dishRepos.save(hamburger4);
			Dish hamburger5 = new Dish("hamburger5", "hamburger5 is good", 11, "https://plus.unsplash.com/premium_photo-1675252369719-dd52bc69c3df?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=387&q=80",burgerKing);
			dishRepos.save(hamburger5);
			Dish hamburger6 = new Dish("hamburger6", "hamburger6 is good", 12, "https://plus.unsplash.com/premium_photo-1668618295237-f1d8666812c9?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80", burgerKing);
			dishRepos.save(hamburger6);
			Dish hamburger7 = new Dish("hamburger7", "hamburger7 is good", 12, "https://images.unsplash.com/photo-1596956470007-2bf6095e7e16?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=429&q=80", burgerKing);
			dishRepos.save(hamburger7);
			Dish hamburger8 = new Dish("hamburger8", "hamburger8 is good", 12, "https://images.unsplash.com/photo-1585238341267-1cfec2046a55?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=574&q=80", burgerKing);
			dishRepos.save(hamburger8);
			Dish indianfood5 = new Dish("indianfood5", "indianfood5 is good", 11, "https://images.unsplash.com/photo-1505253758473-96b7015fcd40?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=400&q=80", grahamMasala);
			dishRepos.save(indianfood5);
			Dish indianfood6 = new Dish("indianfood6", "indianfood6 is good", 12, "https://images.unsplash.com/photo-1567188040759-fb8a883dc6d8?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1017&q=80", grahamMasala);
			dishRepos.save(indianfood6);
			Dish indianfood7 = new Dish("indianfood7", "indianfood7 is good", 12, "https://images.unsplash.com/photo-1517244683847-7456b63c5969?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=388&q=80", grahamMasala);
			dishRepos.save(indianfood7);
			Dish indianfood8 = new Dish("indianfood8", "indianfood8 is good", 12, "https://images.unsplash.com/photo-1567337710282-00832b415979?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1030&q=80", grahamMasala);
			dishRepos.save(indianfood8);
			Dish turkishfood5 = new Dish("turkishfood5", "turkishfood5 is good", 11, "https://images.unsplash.com/photo-1603360946369-dc9bb6258143?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=435&q=80", istabulOriental);
			dishRepos.save(turkishfood5);
			Dish turkishfood6 = new Dish("turkishfood6", "turkishfood6 is good", 12, "https://images.unsplash.com/photo-1530469912745-a215c6b256ea?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=282&q=80", istabulOriental);
			dishRepos.save(turkishfood6);
			Dish turkishfood7 = new Dish("turkishfood7", "turkishfood7 is good", 12, "https://images.unsplash.com/photo-1555939594-58d7cb561ad1?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=387&q=80", istabulOriental);
			dishRepos.save(turkishfood7);
			Dish turkishfood8 = new Dish("turkishfood8", "turkishfood8 is good", 12, "https://images.unsplash.com/photo-1595777216528-071e0127ccbf?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=870&q=80", istabulOriental);
			dishRepos.save(turkishfood8);
			Dish rollingFood5 = new Dish("rollingFood5", "rollingFood5 is good", 11, "https://images.unsplash.com/photo-1603360946369-dc9bb6258143?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=435&q=80", rolls);
			dishRepos.save(rollingFood5);
			Dish rollingFood6 = new Dish("rollingFood6", "rollingFood6 is good", 12, "https://images.unsplash.com/photo-1458130535563-1729c33181cd?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=870&q=80", rolls);
			dishRepos.save(rollingFood6);
			Dish rollingFood7 = new Dish("rollingFood7", "rollingFood7 is good", 12, rolls);
			dishRepos.save(rollingFood7);
			Dish rollingFood8 = new Dish("rollingFood8", "rollingFood8 is good", 12, rolls);
			dishRepos.save(rollingFood8);
			Dish lekkeriFood5 = new Dish("lekkeriFood5", "lekkeriFood5 is good", 11, "https://images.unsplash.com/photo-1594266063697-304befca9629?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80", lekker61);
			dishRepos.save(lekkeriFood5);
			Dish lekkeriFood6 = new Dish("lekkeriFood6", "lekkeriFood6 is good", 12, "https://images.unsplash.com/photo-1498092590708-048e0e2f46d7?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=870&q=80", lekker61);
			dishRepos.save(lekkeriFood6);
			Dish lekkeriFood7 = new Dish("lekkeriFood7", "lekkeriFood7 is good", 12, lekker61);
			dishRepos.save(lekkeriFood7);
			Dish lekkeriFood8 = new Dish("lekkeriFood8", "lekkeriFood8 is good", 12, lekker61);
			dishRepos.save(lekkeriFood8);
			
		};
	}

}
