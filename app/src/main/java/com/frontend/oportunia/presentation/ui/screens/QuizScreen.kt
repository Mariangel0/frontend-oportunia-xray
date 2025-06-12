package com.frontend.oportunia.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.oportunia.R
import com.frontend.oportunia.presentation.ui.components.HeaderType
import com.frontend.oportunia.presentation.ui.layout.MainLayout
import com.frontend.oportunia.presentation.viewmodel.QuizViewModel
import com.frontend.oportunia.presentation.viewmodel.StreakViewModel


@Composable
fun QuizScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    quizViewModel: QuizViewModel,
    streakViewModel: StreakViewModel,
    studentId: Long
) {
    LaunchedEffect(studentId) {
        quizViewModel.resetQuiz()
        quizViewModel.initializeQuiz(
            userId = studentId,
            topic = "Preguntas de entrevista",
            difficulty = "medium"
        )
    }

    val questions by quizViewModel.questions.collectAsState()
    val currentIndex by quizViewModel.currentIndex.collectAsState()
    val evaluations by quizViewModel.evaluations.collectAsState()
    val isLoading by quizViewModel.isLoading.collectAsState()
    val isLoadingNext by quizViewModel.isLoadingNext.collectAsState()

    if (questions.isEmpty() && isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val currentQuestion = questions.getOrNull(currentIndex) ?: return
    val evaluation = evaluations[currentQuestion.question]
    val isAnswered = evaluation != null
    evaluation?.selectedOption

    var localSelected by remember { mutableStateOf<String?>(null) }

    MainLayout(
        paddingValues = paddingValues,
        headerType = HeaderType.BACK,
        title = stringResource(R.string.quiz_title),
        onBackClick = { navController.navigateUp() }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            InteractiveQuizProgressIndicator(
                currentQuestion = currentIndex + 1,
                totalQuestions = 5,
                evaluations = evaluations,
                onQuestionClick = { questionIndex ->
                    quizViewModel.goToQuestion(questionIndex)
                    localSelected = null
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = currentQuestion.question,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                currentQuestion.options.forEach { option ->
                    val backgroundColor = when {
                        evaluation == null -> null
                        evaluation.selectedOption == option && evaluation.isCorrect -> Color(0xFF4CAF50)
                        evaluation.selectedOption == option && !evaluation.isCorrect -> Color(0xFFF44336)
                        !evaluation.isCorrect && evaluation.correctAnswer == option -> Color(0xFF4CAF50)
                        else -> null
                    }

                    if (backgroundColor != null) {
                        QuizColoredAnswerOption(
                            text = option,
                            backgroundColor = backgroundColor,
                            onClick = { }
                        )
                    } else {
                        QuizAnswerOption(
                            text = option,
                            isSelected = localSelected == option,
                            onClick = {
                                if (!isAnswered) {
                                    localSelected = option
                                    quizViewModel.answerQuestion(studentId, option)
                                }
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Custom button that shows loading state
                NextQuizButton(
                    text = if (currentIndex == 4)
                        stringResource(R.string.finish)
                    else
                        stringResource(R.string.next),
                    enabled = isAnswered && !isLoadingNext,
                    isLoading = isLoadingNext,
                    onClick = {
                        if (currentIndex == 4) {
                            quizViewModel.markQuizCompleted(studentId)
                            streakViewModel.loadStreak(studentId)
                            navController.navigateUp()
                        } else {
                            localSelected = null
                            quizViewModel.nextQuestion(studentId)
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun NextQuizButton(
    text: String,
    enabled: Boolean,
    isLoading: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = MaterialTheme.colorScheme.outline
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        if (isLoading) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
                Text(
                    text = stringResource(R.string.loading_generic), // You might need to add this string resource
                    color = Color.White,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        } else {
            Text(
                text = text,
                color = if (enabled) Color.White else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Composable
private fun InteractiveQuizProgressIndicator(
    currentQuestion: Int,
    totalQuestions: Int,
    evaluations: Map<String, Any>,
    onQuestionClick: (Int) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "$currentQuestion/$totalQuestions",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 1..totalQuestions) {
                val index = i - 1
                val isAnswered = evaluations.values.elementAtOrNull(index) != null
                val isCurrent = index == currentQuestion - 1
                val isAccessible = isAnswered || isCurrent || index < currentQuestion - 1

                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clickable(enabled = isAccessible) {
                            if (isAccessible) onQuestionClick(i - 1) // Convert to 0-based index
                        }
                        .background(
                            color = when {
                                isCurrent -> MaterialTheme.colorScheme.primary
                                isAnswered -> MaterialTheme.colorScheme.tertiary
                                else -> MaterialTheme.colorScheme.outline
                            },
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (isAnswered && !isCurrent) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = stringResource(R.string.answered),
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    } else {
                        Text(
                            text = "$i",
                            color = Color.White,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Progress line between circles
                if (i < totalQuestions) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(2.dp)
                            .background(
                                color = if (i < currentQuestion)
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.outline
                            )
                    )
                }
            }
        }
    }
}

@Composable
private fun QuizAnswerOption(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected)
                MaterialTheme.colorScheme.tertiaryContainer
            else
                MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 4.dp else 2.dp
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = if (isSelected)
                    MaterialTheme.colorScheme.onPrimaryContainer
                else
                    MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun QuizColoredAnswerOption(
    text: String,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
            )
        }
    }
}