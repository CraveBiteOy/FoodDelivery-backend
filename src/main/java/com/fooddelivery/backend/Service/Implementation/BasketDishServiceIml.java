package com.fooddelivery.backend.Service.Implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.backend.Exception.EntityNotFoundException;
import com.fooddelivery.backend.Models.Basket;
import com.fooddelivery.backend.Models.BasketDish;
import com.fooddelivery.backend.Models.Dish;
import com.fooddelivery.backend.Repository.BasketDishRepos;
import com.fooddelivery.backend.Repository.BasketRepos;
import com.fooddelivery.backend.Service.BasketDishService;
import com.fooddelivery.backend.Service.DishService;

@Service
public class BasketDishServiceIml implements BasketDishService {
    @Autowired
    DishService dishService;
    @Autowired
    BasketRepos basketRepos;
    @Autowired
    BasketDishRepos basketDishRepos;

    @Override
    public BasketDish turnDishIntoBasketDish(Long dishId, Long basketId, int quantity) {
        Dish dish = dishService.getById(dishId);
        Basket basket = findBasketByid(basketId);
        Optional<BasketDish> entity = basketDishRepos.findByDishAndBasket(dish, basket);
        if(!entity.isPresent()) {
            BasketDish basketDish = new BasketDish(quantity, dish, basket);
            basketDish = basketDishRepos.save(basketDish);

            // update basket
            basket.getBasketDishes().add(basketDish);
            basket.setQuantity(basket.getQuantity() + quantity);
            basket.setTotal(basket.getTotal() + (quantity * dish.getPrice()));
            basketRepos.save(basket);

            return basketDish;
        } else {
            BasketDish basketDish = entity.get();

            return update(basketDish.getId(), quantity);
        }
    }

    @Override
    public BasketDish update(Long basketDishID, int quantity) {
        BasketDish basketDish = getById(basketDishID);
        Dish dish = basketDish.getDish();
        Basket basket = basketDish.getBasket();

         // reset basket
        basket.setTotal(basket.getTotal() - (basketDish.getQuantity() * dish.getPrice()) + (quantity * dish.getPrice()));
        basket.setQuantity(basket.getQuantity() - basketDish.getQuantity() + quantity);
        basketRepos.save(basket);

        basketDish.setQuantity(quantity);
        basketDish = basketDishRepos.save(basketDish);

        return basketDish;
    }

    @Override
    public BasketDish getByDishAndBasket(Long dishId, Long basketId) {
        Dish dish = dishService.getById(dishId);
        Basket basket = findBasketByid(basketId);
        Optional<BasketDish> entity = basketDishRepos.findByDishAndBasket(dish, basket);
         if(!entity.isPresent()) {
            return null;
        } else {
            BasketDish basketDish = entity.get();
            return basketDish;
        }
    }

    @Override
    public BasketDish getById(Long id) {
         Optional<BasketDish> entity = basketDishRepos.findById(id);
         if(!entity.isPresent()) {
            throw new EntityNotFoundException("the basket dish not found");
        } else {
            BasketDish basketDish = entity.get();
            return basketDish;
        }
    }

    @Override
    public void removeBasketDish(Long basketDishId) {
        BasketDish basketDish = getById(basketDishId);
        Basket basket = basketDish.getBasket();

        // update basket
        basket.getBasketDishes().remove(basketDish);
        basket.setQuantity(basket.getQuantity() - basketDish.getQuantity());
        basket.setTotal(basket.getTotal() - (basketDish.getQuantity() * basketDish.getDish().getPrice()));
        basketRepos.save(basket);

        basketDishRepos.delete(basketDish);
    }

    

    @Override
    public List<BasketDish> getByBasket(Long basketId) {
        Basket basket = findBasketByid(basketId);
        List<BasketDish> list = basketDishRepos.findByBasket(basket);
        return list;
    }

    private Basket findBasketByid(Long basketId) {
        Optional<Basket> entity = basketRepos.findById(basketId);
        if(!entity.isPresent()) {
            throw new EntityNotFoundException("the basket not found");
        }
        return entity.get();
    }
}
