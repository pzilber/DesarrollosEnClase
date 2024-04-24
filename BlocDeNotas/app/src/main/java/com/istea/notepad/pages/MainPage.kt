package com.istea.notepad.pages

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.istea.notepad.Nota
import com.istea.notepad.ui.theme.NotepadTheme

@Composable
fun MainPage(modifier: Modifier = Modifier) {

    val navHostController = rememberNavController()
    val notas = remember { mutableStateListOf<Nota>() }

    Scaffold(
        modifier = modifier,
        topBar = { MainTopAppBar() },
        floatingActionButton = {
            BotonCrear(navHostController)
        }

    ) {
        MainNavHost(
            modifier = Modifier.padding(it),
            navHostController = navHostController,
            notas = notas
        )
    }
}

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navHostController : NavHostController,
    notas : MutableList<Nota>
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = "lista"
    ) {
        composable("lista") {
            ListaPage(
                notas = notas,
                onNotaSelected = {
                    navHostController.navigate("detalle/$it")
                }
            )
        }
        composable(
            route = "detalle/{notaId}",
            arguments = listOf(
                navArgument( name = "notaId"){
                    type = NavType.StringType
                }
            )
        ) {
            val notaId = it.arguments?.getString("notaId")
            if (notaId != null) {
                val nota = notas.filter { it.titulo == notaId }.first()
                DetallePage(nota = nota)
            }
        }
        composable("crearNota") {
            CrearNotaPage(
                onNuevaNota = { titulo, cuerpo ->
                    val nota = Nota(titulo= titulo, cuerpo = cuerpo)
                    navHostController.popBackStack()
                    notas.add(nota)
                }
            )
        }
    }
}

@Composable
fun BotonCrear(navHostController: NavHostController ){
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    if (currentRoute == "lista") {
        FloatingActionButton(onClick = { navHostController.navigate("crearNota") }) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "nueva nota")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(){
    TopAppBar(
        title = {
            Text(text = "Notepad")
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Preview(showBackground = true)
@Composable
fun MainPagePreview() {
    NotepadTheme {
        MainPage()
    }
}