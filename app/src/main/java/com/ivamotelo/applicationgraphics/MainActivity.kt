/**
 * https://developer.android.com/guide/topics/graphics
 * Permissão de leitura de armazenamento externo - 'READ_EXTERNAL_STORAGE'
 *A permissão será de forma dinâmica, ou seja, se o usuário já tiver autorização
 * ao clicar no botão, o processo continuará, porém, se o usuário não tiver ainda
 * a permissão de acesso, então a mesma será solicitada.
 *
 * Atenção! neste projeto foi utilzada a biblioteca 'Kotlin.extensions', que foi descontinuada
 *
 * É verificado se a versão do Android é maior ou igual a 'M' de Mashmelhow' - 23, se positivo,
 * É NECESSÀRIO o pedido de permissão ao usuário, senão (else)  NÂO é necessário a permissão
 * Se a versão for maior que 'M', então o app verifica se já foi dada a permissão (if indentado)
 */

package com.ivamotelo.applicationgraphics

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

//import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_pick_Image: Button
        btn_pick_Image = findViewById<Button>(R.id.btn_pick_Image)

        btn_pick_Image.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED
                ) {
                    val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permission, PERMISSION_CODE)
                } else {
                    pickImageFromGalery()
                }
            } else {
                pickImageFromGalery()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGalery()
                } else {
                    Toast.makeText(this, "permissão negada", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    private fun pickImageFromGalery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && resultCode == IMAGE_PICK_CODE) {
            image_view.setImageURI(data?.data)
        }
    }

    companion object {
        private val PERMISSION_CODE = 1000
        private val IMAGE_PICK_CODE = 1001
    }
}