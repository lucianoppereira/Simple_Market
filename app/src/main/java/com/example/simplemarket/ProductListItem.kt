package com.example.simplemarket


import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.simplemarket.databinding.ProductItemBinding
import com.xwray.groupie.viewbinding.BindableItem

class ProductListItem(val product: Product) : BindableItem<ProductItemBinding>() {

    override fun getLayout(): Int {
        return R.layout.product_item
    }

    override fun bind(viewBinding: ProductItemBinding, position: Int) {

        viewBinding.productTitle.text = product.title
        viewBinding.productPrice.text = product.price
        Glide.with(viewBinding.sampleImage.context)
            .load(product.image)
            .into(viewBinding.sampleImage)

        viewBinding.cartAddTo.setOnClickListener {


            val item = CartItem(product)

            MarketCart.addItem(item, viewBinding.cartAddTo.context)

            Toast.makeText(
                viewBinding.cartAddTo.context,
                R.string.add_to_Cart,
                Toast.LENGTH_SHORT
            ).show()

        }

        viewBinding.cartRemoveFrom.setOnClickListener {

            val item = CartItem(product)

            MarketCart.directRemoveItem(item, viewBinding.cartRemoveFrom.context)
            Toast.makeText(
                viewBinding.cartAddTo.context,
                R.string.remove_from_Cart,
                Toast.LENGTH_SHORT
            ).show()

        }

    }

    override fun initializeViewBinding(view: View): ProductItemBinding {
        return ProductItemBinding.bind(view)
    }

}