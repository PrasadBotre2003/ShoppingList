package com.example.shoppinglist.ui.theme

import android.text.style.ClickableSpan
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

data class ShoppingItem(var id:Int ,
                        var name:String ,
                        var quantity:Int,
                        var isediting :Boolean = false){

}




@Composable
fun shoppingAppList() {


    var sItem by remember { mutableStateOf(listOf<ShoppingItem>()) }
    var ShowDialog by remember { mutableStateOf(false) }
    var ItemName by remember { mutableStateOf("") }
    var ItemQuantity by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    )
    {
        Button(onClick = { ShowDialog = true },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        {
            Text(text = "ADD....ITEM")
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(sItem) { item ->
                if (item.isediting) {
                    ShoppingItemEditor(item = item, onEditComplete = {editedName,editedQuantity->

                        sItem = sItem.map { it.copy(isediting = false) }
                        val editeditem = sItem.find{it.id == item.id}
                        editeditem?.let {


                            it.name = editedName
                            it.quantity = editedQuantity
                        }

                    } )


                }
                else {
                    ShoppingListItem(item = item, onEditClick = {
                        //finding item changing booleAN
                        sItem = sItem.map {
                            it.copy(isediting = it.id == item.id)
                        }


                    }, onDeleteClick = {
                     sItem = sItem-item
                    })



                    }

                }
            }
        }



    if(ShowDialog){
       AlertDialog(onDismissRequest = {ShowDialog = false },
           confirmButton = {





                           
                           Row (modifier = Modifier
                               .fillMaxWidth()
                               .padding(8.dp),
                               horizontalArrangement = Arrangement.SpaceBetween){
                               Button(onClick = {
                                   if (ItemName.isNotBlank()) {

                                       val newItem = ShoppingItem(
                                           id = sItem.size + 1,
                                           name = ItemName,
                                           quantity = ItemQuantity.toInt()

                                       )

                                       sItem = sItem + newItem
                                       ShowDialog = false
                                       ItemName = ""

                                   }


                               })

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
@Composable
fun ShoppingItemEditor(item: ShoppingItem, onEditComplete: (String, Int) -> Unit){
    var editedName by remember { mutableStateOf(item.name) }
    var editedQuantity by remember { mutableStateOf(item.quantity.toString()) }
    var isEditing by remember { mutableStateOf(item.isediting) }

    Row(modifier = Modifier
        .fillMaxWidth()
        .background(color = Color.White)

        .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    )
    {
        Column {
            BasicTextField(
                value= editedName,
                onValueChange = {editedName = it},
                singleLine = true,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(8.dp)
            )
            BasicTextField(
                value= editedQuantity,
                onValueChange = {editedQuantity = it},
                singleLine = true,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(8.dp)
            )
        }

        Button(
            onClick = {
                isEditing = false
                onEditComplete(editedName, editedQuantity.toIntOrNull() ?: 1)
            }
        ){
            Text("Save")
        }
    }


}


@Composable
fun ShoppingListItem(
    item: ShoppingItem,
    onEditClick:()->Unit,

    onDeleteClick: ()->Unit,

) {

    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .border(
                border = BorderStroke(2.dp, Color(0xFF018786)),
                shape = RoundedCornerShape(20)

            )
    ) {

        Text(text = item.name, modifier = Modifier.padding(8.dp))
        Text(text = "Qty : ${item.quantity}", modifier = Modifier.padding(8.dp))
        Row(modifier = Modifier.padding(8.dp),Arrangement.SpaceBetween) {
            IconButton(onClick = onEditClick) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = null)


            }

            IconButton(onClick = onDeleteClick) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
            }
        } }
    }

