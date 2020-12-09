# infinite-viewpager2
An Infinite ViewPager2  

# Implementation

Add the JitPack repository to your build file

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
 
Add the dependency in your build.gradle file


	        implementation "com.github.watermelon-mann.infinite-viewpager2:infinite-viewpager2:$infinite_view_pager_version"

Current version is  1.0


# How to use

The implementation for fragments is very easy. Just use the builder like in example below.

	        val viewPager: ViewPager2 = InfiniteViewPager.Builder(this, findViewById(R.id.view_pager))
            .withFragments(
                Fragment1::class.java,
                Fragment2::class.java,
                Fragment3::class.java
            )
            .build()
						


You can also pass a list instead of varargs

The result is

![](https://github.com/watermelon-mann/infinite-viewpager2/blob/master/GIF_infinite_view_pager_fragments.gif)


For a ViewPager2 with layout items we are going to need DataBinding, in order to make it posible to work with multiple types of items. If you're not familiar with DataBinding, please, go check it out first, to not get confused.
After enabling databinding in gradle, we will need to create a ViewPager's layout item using the corresponding data class, like below.



Item.kt

	

	data class Item(
    val id: Int,
    val text: String
	)



layout_item.xml
							
	<layout>
    <data>

        <variable
            name="item"
            type="com.example.testivp.Item" />

    </data>


    <androidx.constraintlayout.widget.ConstraintLayout
        xmlns:android="http://schemas.android.com/apk/res/android" 
				android:layout_width="match_parent"
        android:layout_height="match_parent">

        <androidx.appcompat.widget.AppCompatTextView
            android:id="@+id/item_text_view"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:gravity="center"
            android:text="@{item.text}"
            />

    </androidx.constraintlayout.widget.ConstraintLayout>
	</layout>

Assume we have some list of Items


	val listOfItems = listOf(
    	Item(1, "Item 1"),
    	Item(2, "Item 2"),
    	Item(3, "Item 3")
	)


Just convert it to a list of RecyclerViewItems using toRecyclerViewItem() function, passing the layout.xml for layoutId parameter, and the variable name from the layout for variableId parameter. Example below.

		val items = listOfItems.map {
            it.toRecyclerViewItem(R.layout.layout_item, BR.item)
        }
				
Then pass the list of RecyclerViewItems in the Builder

		val viewPager: ViewPager2 = InfiniteViewPager.Builder(this, findViewById(R.id.view_pager))
            .withItems(items)
            .build()
						
You can also pass varargs instead of a list.

The result is

![](https://github.com/watermelon-mann/infinite-viewpager2/blob/master/GIF_infinite_view_pager_items.gif)

Thank you for coming in:) Feel free to message me if you find some issues.

