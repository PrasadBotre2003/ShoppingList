package com.example.shoppinglist.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class ShoppingItem(val id:Int ,
                        var name:String ,
                        var quantity:Int,
                        var editing :Boolean = false){

}

//@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun shoppingAppList(){






    var sItem by remember{ mutableStateOf(listOf<ShoppingItem>()) }
    var ShowDialog by remember { mutableStateOf(false) }
    var ItemName by remember { mutableStateOf("") }
    var ItemQuantity by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center)
    {
        Button(onClick = {ShowDialog = true}, modifier = Modifier.
        align(Alignment.CenterHorizontally))
        {
            Text(text = "ADD....ITEM")
        }
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            items(sItem){

            }
        }

    }
    if(ShowDialog){
       AlertDialog(onDismissRequest = {ShowDialog = false },
           confirmButton = {
               if(ItemName.isNotBlank()){

                   val newItem = ShoppingItem(
                       id = sItem.size+1,
                       name = ItemName,
                       quantity = ItemQuantity.toInt()


                   )
                   sItem = sItem + newItem
                   ShowDialog = false
                   ItemName = ""
               }
                           
                           Row (modifier = Modifier
                               .fillMaxWidth()
                               .padding(8.dp),
                               horizontalArrangement = Arrangement.SpaceBetween){
                               Button(onClick = { /*TODO*/ }) 
                               {
                                   Text(text = "Add")
                                   
                               }
                               Button(onClick = { ShowDialog = false}) {
                                   Text(text = "Cancel")
                               }
                           }
           },
           title = { Text("Add shopping Item")},
           text = {
                 Column {
                     OutlinedTextField(value = ItemName,
                         onValueChange = {ItemName = it },
                         singleLine = true,
                         modifier = Modifier
                             .fillMaxWidth()
                             .padding(10.dp), shape = CircleShape

                     )

                     OutlinedTextField(value = ItemQuantity,
                         onValueChange = {ItemQuantity = it },
                         singleLine = true,
                         modifier = Modifier
                             .fillMaxWidth()
                             .padding(10.dp), shape = CircleShape

                     )
                 }
           }
           )


    }
}