package com.example.projekfuad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.projekfuad.ui.theme.ProjekFuadTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjekFuadTheme {
                val selectedItem = remember { mutableIntStateOf(1) }
                val selectedCharacter = remember { mutableStateOf<String?>(null) }
                val navController = rememberNavController()

                Scaffold(
                    topBar = {
                        TopAppBarWithBackButton(
                            title = selectedCharacter.value ?: when (selectedItem.intValue) {
                                1 -> "Home"
                                2 -> "Grid View"
                                else -> "Profile"
                            },
                            showBackButton = selectedItem.intValue == 3 || selectedCharacter.value != null,
                            onBackClick = {
                                if (selectedCharacter.value != null) {
                                    selectedCharacter.value = null
                                } else {
                                    navController.popBackStack()
                                }
                            }
                        )
                    },
                    bottomBar = { BottomNavigationBar(selectedItem = selectedItem) },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        when {
                            selectedCharacter.value != null -> {
                                CharacterDetailScreen(name = selectedCharacter.value!!)
                            }
                            selectedItem.intValue == 1 -> {
                                HomeScreen(
                                    onCharacterClick = { name -> selectedCharacter.value = name }
                                )
                            }
                            selectedItem.intValue == 2 -> {
                                CharacterGridScreen()
                            }
                            selectedItem.intValue == 3 -> {
                                ProfileScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CharacterGridScreen() {
    val characters = listOf(
        "Brimstone",
        "Viper",
        "Omen",
        "Astra",
        "Killjoy",
        "Cypher",
        "Sage",
        "Chamber",
        "Jett",
        "Phoenix",
        "Reyna",
        "Raze",
        "Yoru",
        "KAY/O",
        "Breach",
        "Sova",
        "Skye",
        "Fade",
        "Gekko"
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(characters) { character ->
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(character, modifier = Modifier.padding(8.dp))
            }
        }
    }
}

@Composable
fun BottomNavigationBar(selectedItem: MutableState<Int>) {
    val items = listOf("I", "II", "III")
    NavigationBar(
        modifier = Modifier.fillMaxWidth()
    ) {
        items.forEachIndexed { index, label ->
            NavigationBarItem(
                selected = selectedItem.value == index + 1,
                onClick = { selectedItem.value = index + 1 },
                label = { Text(text = label) },
                icon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) }
            )
        }
    }
}

@Composable
fun GreetingList(names: List<String>, onCharacterClick: (String) -> Unit, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(names) { name ->
            Greeting(name = name, onClick = { onCharacterClick(name) })
        }
    }
}

@Composable
fun Greeting(name: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Text(
        text = name,
        modifier = modifier
            .padding(16.dp)
            .clickable { onClick() }
    )
}

@Composable
fun ProfileScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
                .border(2.dp, Color.Gray, CircleShape)
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile_picture),
                contentDescription = "Profile Picture",
                modifier = Modifier.fillMaxSize().clip(CircleShape)
            )
        }
        Text(
            text = "Fuad Kadarisman",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Politeknik Negeri Sriwijaya | DIV - TIMD",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 8.dp)
        )
        Text(
            text = "Android Developer | UI/UX Enthusiast | Toxic Gamer",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text("Contact Information", style = MaterialTheme.typography.titleMedium)
        Text(
            text = "Email: fuadkd1603@gmail.com",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 4.dp)
        )
        Text(
            text = "Phone: +62 821 8058 4119",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text("Skills & Interests", style = MaterialTheme.typography.titleMedium)
        Column(modifier = Modifier.padding(top = 8.dp)) {
            Text("• Android Development", style = MaterialTheme.typography.bodyMedium)
            Text("• UI/UX Design", style = MaterialTheme.typography.bodyMedium)
            Text("• Gaming", style = MaterialTheme.typography.bodyMedium)
            Text("• Photography", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProjekFuadTheme {
        ProfileScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingListPreview() {
    ProjekFuadTheme {
        GreetingList(
            names = listOf(
                "Brimstone", "Viper", "Omen", "Astra", "Killjoy", "Cypher", "Sage", "Chamber",
                "Jett", "Phoenix", "Reyna", "Raze", "Yoru", "KAY/O", "Breach", "Sova", "Skye",
                "Fade", "Gekko"
            ),
            onCharacterClick = { name -> println("Clicked on $name") }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarWithBackButton(
    title: String,
    showBackButton: Boolean = false,
    onBackClick: () -> Unit = {}
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onBackClick) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            } else {
                Spacer(modifier = Modifier)
            }
        }
    )
}


@Composable
fun HomeScreen(onCharacterClick: (String) -> Unit) {
    SpacerRow(names = listOf("Breeze", "Haven", "Ascent", "Pearl", "Abyss", "Split", "Bind"), onCharacterClick = onCharacterClick)
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.weight(1f)) {
            CharacterGreetingList(names = listOf("Brimstone", "Viper", "Omen", "Astra", "Killjoy", "Cypher", "Sage", "Chamber", "Jett", "Phoenix", "Reyna", "Raze", "Yoru", "KAY/O", "Breach", "Sova"), onCharacterClick)
        }
    }
}


@Composable
fun Greeting(name: String, onClick: () -> Unit, imageRes: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(16.dp)
            .clickable { onClick() }
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "$name Image",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .padding(end = 16.dp)
        )
        Text(
            text = name,
            modifier = Modifier.padding(16.dp)
        )
    }
}
@Composable
fun CharacterGreetingList(names: List<String>, onCharacterClick: (String) -> Unit, modifier: Modifier = Modifier) {
    val characterImages = mapOf(
        "Brimstone" to R.drawable.brimstone_image,
        "Viper" to R.drawable.viper_image,
        "Omen" to R.drawable.omen_image,
        "Astra" to R.drawable.astra_image,
        "Killjoy" to R.drawable.killjoy_image,
        "Cypher" to R.drawable.cypher_image,
        "Sage" to R.drawable.sage_image,
        "Chamber" to R.drawable.chamber_image,
        "Jett" to R.drawable.jett_image,
        "Phoenix" to R.drawable.phoenix_image,
        "Reyna" to R.drawable.reyna_image,
        "Raze" to R.drawable.raze_image,
        "Yoru" to R.drawable.yoru_image,
        "KAY/O" to R.drawable.kayo_image,
        "Breach" to R.drawable.breach_image,
        "Sova" to R.drawable.sova_image,
        "Skye" to R.drawable.skye_image,
        "Fade" to R.drawable.fade_image,
        "Gekko" to R.drawable.gekko_image
    )

    LazyColumn(modifier = modifier) {
        items(names) { name ->
            characterImages[name]?.let { imageRes ->
                Greeting(name = name, onClick = { onCharacterClick(name) }, imageRes = imageRes)
            }
        }
    }
}

@Composable
fun SpacerGreeting(names: List<String>, onCharacterClick: (String) -> Unit, modifier: Modifier = Modifier) {
    val SpacerImages = mapOf(
        "Breeze" to R.drawable.breeze_image,
        "Haven" to R.drawable.haven_image,
        "Ascent" to R.drawable.ascent_image,
        "Pearl" to R.drawable.pearl_image,
        "Abyss" to R.drawable.abyss_image,
        "Split" to R.drawable.split_image,
        "Bind" to R.drawable.bind_image
    )

    LazyRow(modifier = modifier) {
        items(names) { name ->
            SpacerImages[name]?.let { imageRes ->
                SpacerGreeting(name = name, onClick = { onCharacterClick(name) }, imageRes = imageRes)
            }
        }
    }
}
@Composable
fun SpacerGreeting(name: String, onClick: () -> Unit, imageRes: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(16.dp)
            .clickable { onClick() }
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "$name Image",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .padding(end = 16.dp)
        )
        Text(
            text = name,
            modifier = Modifier.padding(16.dp)
        )
    }
}



@Composable
fun SpacerRow(names: List<String>, onCharacterClick: (String) -> Unit, modifier: Modifier = Modifier) {
    LazyRow(modifier = modifier.padding(8.dp)) {
        items(names) { name ->
            Greeting(name = name, onClick = { onCharacterClick(name) }, modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun SpacerDetailScreen(name: String) {
    val description = SpacerDescriptions[name] ?: "No description available."

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Details about $name",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

val SpacerDescriptions = mapOf(
    "Breeze" to "sdfghsrfghjsfghjsdftgyhmnfhsmneas and block vision.",
    "Haven" to "Vdfghjdfghjdfghjdfghjsion and control areas.",
    "Ascent" to "Omdfghjdghkmdghjdghjj,mlefield with smokes and teleportation.",
    "Pearl" to "Astra ms,rtyuk,msfgnstuk,mestdukl,metsjlp her team dominate the battlefield.",
    "Abyss" to "Kildghjkdtg,dfyhu,rfyik,rfdtyu,ry"
    )

@Composable
fun CharacterDetailScreen(name: String) {
    val description = characterDescriptions[name] ?: "No description available."

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Details about $name",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

val characterDescriptions = mapOf(
    "Brimstone" to "Brimstone is a controller agent who uses incendiary grenades and smoke grenades to control areas and block vision.",
    "Viper" to "Viper is a controller agent with the ability to create toxic clouds and smoke to obstruct the enemy's vision and control areas.",
    "Omen" to "Omen is a controller agent with abilities focused on creating confusion and controlling the battlefield with smokes and teleportation.",
    "Astra" to "Astra is a controller agent who can manipulate gravity and space to create zones that help her team dominate the battlefield.",
    "Killjoy" to "Killjoy is a sentinel agent who uses gadgets like turrets and traps to control areas and help her team with strategic defense.",
    "Cypher" to "Cypher is a sentinel agent known for his ability to gather information and trap enemies using his cameras and tripwires.",
    "Sage" to "Sage is a healer and support character, who can revive teammates and heal allies with her abilities.",
    "Chamber" to "Chamber is a sentinel who can control areas using his traps and deal damage with precision weapons.",
    "Jett" to "Jett is a duelist who excels in mobility and fast-paced combat, with abilities that allow her to dash and glide through the air.",
    "Phoenix" to "Phoenix is a duelist who can heal himself and deal fire damage to enemies. His abilities revolve around fire and healing.",
    "Reyna" to "Reyna is a duelist with abilities focused on healing, blinding enemies, and becoming more powerful after every kill.",
    "Raze" to "Raze is a duelist who specializes in explosive damage, with abilities that deal heavy area damage to enemies.",
    "Yoru" to "Yoru is a duelist who uses deception and teleportation to confuse and ambush enemies.",
    "KAY/O" to "KAY/O is an initiator with abilities focused on suppressing enemies and providing tactical advantages for his team.",
    "Breach" to "Breach is an initiator with abilities that focus on disrupting enemies with concussive blasts and disabling their vision.",
    "Sova" to "Sova is an initiator who uses his recon abilities to locate enemies and provide information to his team.",
    "Skye" to "Skye is a support agent who heals and supports her team with flashes and an ability to heal teammates over time.",
    "Fade" to "Fade is an initiator who specializes in detecting enemies and impairing their abilities with her darkness-related powers.",
    "Gekko" to "Gekko is an agent who uses small creatures to attack enemies, heal allies, and control space."
)
