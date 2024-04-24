package com.istea.notepad.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.istea.notepad.Nota
import com.istea.notepad.ui.theme.NotepadTheme

@Composable
fun DetallePage(
    modifier: Modifier = Modifier,
    nota : Nota
) {
    Column(modifier = modifier) {
        Text(
            text = nota.titulo
        )
        Text(
            text = nota.cuerpo
        )
    }

}

@Preview(showBackground = true)
@Composable
fun DetallePagePreview() {
    NotepadTheme {
        DetallePage(nota = Nota("Titulo", cuerpo = "cuerpo de la nota"))
    }
}