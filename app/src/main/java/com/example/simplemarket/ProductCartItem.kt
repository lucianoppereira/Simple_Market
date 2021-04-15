package com.example.simplemarket


import android.view.View
import com.bumptech.glide.Glide
import com.example.simplemarket.databinding.ProductCartItemBinding
import com.xwray.groupie.viewbinding.BindableItem


class ProductCartItem(val cartItem: CartItem) : BindableItem<ProductCartItemBinding>() {

    override fun getLayout(): Int {
        return R.layout.product_cart_item
    }

    override fun bind(viewBinding: ProductCartItemBinding, position: Int) {

        viewBinding.cartProductTitle.text = cartItem.product.title
        viewBinding.cartProductPrice.text = "$${cartItem.product.price}"
        viewBinding.quantity.text = cartItem.quantity.toString()
        Glide.with(viewBinding.sampleImage.context)
            .load(cartItem.product.image)
            .into(viewBinding.sampleImage)

        viewBinding.cartRemoveFrom.setOnClickListener {

            val item = cartItem
            MarketCart.directRemoveItem(item, viewBinding.cartRemoveFrom.context)
            notifyChanged()

        }

        viewBinding.buttonRest.setOnClickListener {

            val item = cartItem
            MarketCart.removeItem(item, viewBinding.cartRemoveFrom.context)
            viewBinding.quantity.text = cartItem.quantity.toString()
            notifyChanged()

        }

        viewBinding.buttonSum.setOnClickListener {

            val item = cartItem

            MarketCart.addItem(item, viewBinding.buttonSum.context)
            viewBinding.quantity.text = cartItem.quantity.toString()
            notifyChanged()

        }


    }


    override fun initializeViewBinding(view: View): ProductCartItemBinding {
        return ProductCartItemBinding.bind(view)
    }


}