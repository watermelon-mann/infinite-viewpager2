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
 
Add the dependancy in your build.gradle file


	        implementation "com.github.watermelon-mann.infinite-viewpager2:infinite-viewpager2:$infinite_view_pager_version"

Current version is  0.43


# How to use

The implementation for fragments is very easy

	        val viewPager: ViewPager2 = InfiniteViewPager.Builder(this, findViewById(R.id.view_pager))
            .withFragments(
                Fragment1::class.java,
                Fragment2::class.java,
                Fragment3::class.java
            )
            .build()
						


You can also pass a list instead of varargs


For layout items you are going to need DataBinding. If you're not familiar with DataBinding please go check it out first, to not get confused.
After enabling databinding in gradle, you need to use it on your layout item.



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


Just convert it to a list of RecyclerViewItems like this

		val items = listOfItems.map {
            it.toRecyclerViewItem(R.layout.layout_item, BR.item)
        }
				
Then pass the list of RecyclerViewItems in the Builder

		val viewPager: ViewPager2 = InfiniteViewPager.Builder(this, findViewById(R.id.view_pager))
            .withItems(items)
            .build()
						
You can also pass varargs instead of a list.

Thank you for checkin:) Feel free to message me if you find some issues.

