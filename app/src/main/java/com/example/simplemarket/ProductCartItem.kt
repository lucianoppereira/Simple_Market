package com.example.simplemarket


import android.annotation.SuppressLint
import android.view.View
import com.bumptech.glide.Glide
import com.example.simplemarket.databinding.ProductCartItemBinding
import com.xwray.groupie.viewbinding.BindableItem


class ProductCartItem(val cartItem: CartItem, val callback: ItemCallback) :
    BindableItem<ProductCartItemBinding>() {

    override fun getLayout(): Int {
        return R.layout.product_cart_item
    }

    @SuppressLint("SetTextI18n")
    override fun bind(viewBinding: ProductCartItemBinding, position: Int) {


        viewBinding.cartProductTitle.text = cartItem.product.title
        viewBinding.cartProductPrice.text =
            "$${CartSingleton.priceFormat(cartItem.product.price.toString())}"
//            CartSingleton.getConversion(
//                viewBinding.cartProductTitle.context,

//            )
//        }"
        viewBinding.quantity.text = cartItem.quantity.toString()
        Glide.with(viewBinding.sampleImage.context)
            .load(cartItem.product.image)
            .into(viewBinding.sampleImage)

        viewBinding.cartRemoveFrom.setOnClickListener {

            cartItem.quantity = 0
            viewBinding.quantity.text = cartItem.quantity.toString()
            MarketCart.directRemoveItem(cartItem, viewBinding.cartRemoveFrom.context)

            callback.onItemRemove(ProductCartItem@ this)

        }

        viewBinding.buttonRest.setOnClickListener {

            if (cartItem.quantity > 0) cartItem.quantity--

            viewBinding.quantity.text = cartItem.quantity.toString()
            MarketCart.removeItem(cartItem, viewBinding.cartRemoveFrom.context)

            notifyChanged()

            callback.onItemRemove(ProductCartItem@ this)

        }

        viewBinding.buttonSum.setOnClickListener {

            cartItem.quantity++
            MarketCart.addItem(cartItem, viewBinding.buttonSum.context)
            viewBinding.quantity.text = cartItem.quantity.toString()

            callback.calcTotals()
        }


    }


    override fun initializeViewBinding(view: View): ProductCartItemBinding {
        return ProductCartItemBinding.bind(view)
    }


}