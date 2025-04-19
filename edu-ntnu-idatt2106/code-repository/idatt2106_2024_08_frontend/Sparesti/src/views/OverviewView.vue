<script setup>
import NavBar from "@/components/NavBar.vue";
import "../assets/baseStyle.css";
import {onMounted, ref, watch} from "vue";
import {useTokenStore} from "@/stores/token.js";
import {navigateToPage} from "@/utils/httputils.js";
import {ip} from "@/utils/httputils.js";
import axios from "axios";
import router from "@/router/index.js";
import { Chart as ChartJS, ArcElement, Legend, Tooltip } from 'chart.js';
import { computed } from 'vue';

import {Doughnut} from "vue-chartjs";

const incomes = ref(0);
const expenses = ref(0);
const expensesByCategory = ref([]);
const ChartData = ref(null);
const dateError = ref('');


/**
 * Make an attempt to navigate to the '/overview' page and log any error messages.
 */
(async () => {
  try{
    await navigateToPage('/overview')
  } catch (error){
    console.error('Error fetching data:', error);
  }
})();
const editing = ref(false);
const questionVisibility = () => {
  editing.value = !editing.value;
}
/**
 * Fetches data about income and expenses from the backend based on the selected date range.
 * Displays error messages when dates are invalid.
 *
 * @returns {Promise<void>} A promise that completes when the data is retrieved
 */
const fetchOverviewData = async () => {
  try{
    const tokenStore = useTokenStore();
    const startDateInput = document.getElementById('startDate').value;
    const endDateInput = document.getElementById('endDate').value;
    const startDate = new Date(startDateInput);
    const endDate = new Date(endDateInput);
    const today = new Date(new Date().toDateString());
    today.setHours(23, 59, 59, 999);

    dateError.value = '';

    if (startDate > endDate) {
      dateError.value = 'Sluttdato kan ikke være tidligere enn startdato!';
      return;
    }

    if (endDate > today) {
      dateError.value = 'Du kan ikke velge en dato som ikke har vært ennå!';
      return;
    }

    if (startDateInput && endDateInput){
      const startDate = new Date(startDateInput).toISOString();
      const endDate = new Date(endDateInput).toISOString();

      const response = await axios.get(`https://${ip}:8443/api/overview/`+ tokenStore.getLoggedInUser() + `/date_range?startDate=${startDate}&endDate=${endDate}`, tokenStore.getAxiosAuthorizationConfig());

      await navigateToPage('/overview')

      if (response.status === 200) {
        const userData = response.data;

        incomes.value = userData.incomes.toFixed(3);
        expenses.value = userData.expenses.toFixed(3);
        expensesByCategory.value = userData.expensesByCategory;

        setupDoughnutChart()
      } else {
        await router.push('/login')
      }
    } else {
      const currentDate = new Date();
      const startDate = new Date(currentDate.getTime() - 30 * 24 * 60 * 60 * 1000);
      const endDate = currentDate.toISOString();

      document.getElementById('startDate').valueAsDate = startDate;
      document.getElementById('endDate').valueAsDate = new Date(endDate);

      try {
        const response = await axios.get(`https://${ip}:8443/api/overview/`+ tokenStore.getLoggedInUser() + `/date_range?startDate=${startDate.toISOString()}&endDate=${endDate}`, tokenStore.getAxiosAuthorizationConfig());

        if (response.status === 200) {
          const userData = response.data;
          incomes.value = userData.incomes.toFixed(3);
          expenses.value = userData.expenses.toFixed(3);
          expensesByCategory.value = userData.expensesByCategory;
          setupDoughnutChart();
          await navigateToPage('/overview');
        } else {
          await router.push('/login');
        }
      } catch (error) {
        console.error('Error fetching data:', error);
      }    }
  } catch (error){
    console.error('Error fetching data:', error);
  }
};

/**
 * Sets up the donut chart with the data for the expenses by category.
 *
 * @returns {object} The data for the donut chart.
 */
const setupDoughnutChart = () => {
  const ChartData = ref(null);
  ChartJS.register(ArcElement, Tooltip, Legend)
  if (expensesByCategory.value.length > 0){
    ChartData.value = {
      labels: [
        'Dagligvarer',
        'Reise',
        'Streaming',
        'Restaurant',
        'Klær',
        'Annet'
      ],
      datasets: [{
        label: 'Oversikt over utgifter',
        data: [
          expensesByCategory.value[0],
          expensesByCategory.value[1],
          expensesByCategory.value[2],
          expensesByCategory.value[3],
          expensesByCategory.value[4],
          expensesByCategory.value[5]
        ],
        backgroundColor: [
          'rgb(217,97,160)',
          'rgb(54, 162, 235)',
          'rgb(255, 205, 86)',
          'rgb(102,37,89)',
          'rgb(129,200,28)',
          'rgb(219,9,56)'
        ],
        hoverOffset: 5,
        animation: false
      }]
    };

    return ChartData.value;
  }
}

/**
 * Defines the function to run when the component is mounted.
 */
onMounted(() => {
  fetchOverviewData();
});

/**
 * Monitors changes in the expenses and income data and updates the donut chart in the event of changes.
 */
watch(expensesByCategory, () => {
  if (expensesByCategory.value.length > 0) {
    ChartData.value = setupDoughnutChart();
  }
});


/**
 * Formats the expenses and incomes to display with two decimal places and
 * to use the Norwegian/ISO currency format with whitespace as thousand-separator
 * and comma on the line as decimal separator.
 *
 * @type {ComputedRef<string>}
 */
const formattedExpenses = computed(() => {
  return new Intl.NumberFormat('no-NB', {minimumFractionDigits: 2}).format(expenses.value)
})

/**
 * Computes and formats the incomes value.
 *
 * @function formattedIncomes
 * @return {string} The formatted incomes value.
 */
const formattedIncomes = computed(() => {
  return new Intl.NumberFormat('no-NB', {minimumFractionDigits: 2}).format(incomes.value)
})

</script>

<template>
  <div class="flex-container">
    <div class="header">
      <h1 class="title">Oversikt</h1>
    </div>
    <div class="main">
      <div class="home-content">
        <div class="top">
          <div v-if="dateError" class="error">{{ dateError }}</div>
          <div class="dates">
            <label class="dato">Fra dato: </label>
            <input type="date" id="startDate">
            <label class="dato">Til dato: </label>
            <input type="date" id="endDate">
            <button class="updateButton" @click="fetchOverviewData">Oppdater</button>
          </div>
          <div class="doughnut-container">
            <Doughnut class="doughnutChart" v-if="ChartData"  :data="ChartData" :options="{maintainAspectRatio: false, plugins: {
              legend: {
                display: true,
                position: 'right',
                labels: {
                  font: {
                    size: 14,
                    weight: 'bold'
                  },
                  color: 'black'
                },
              },
            },}" />
          </div>
        </div>
        <div class="bottomHalf">
          <div class="text">
            <p>Dine utgifter er:</p>
            <input class="expenses" type="text" :value="formattedExpenses" disabled>
          </div>
          <div class="text">
            <p>Dine inntekter er:</p>
            <input class="incomes" type="text" :value="formattedIncomes" disabled>
          </div>
        </div>
        <div class="info">
          <div class="info-container">
            <p class="intro">
              Her ser du en oversikt over inntekter og utgifter.
              <br>
              Denne er basert på transaksjonene fra banken din. Kanskje ser du noen områder hvor du har rom for å spare litt?
              <br>
              <br>
              Du kan skjule og vise kategorier ved trykke på dem.
            </p>
          </div>
      </div>
      </div>
    </div>
    <div class="footer">
      <nav-bar/>
    </div>
  </div>
</template>

<style scoped>

.info-container {
  margin-top: 40px;
  background-color: whitesmoke;
  min-width: 260px;
  max-width: 40vw;
  border-radius: 30px;
  text-align: center;
  padding: 10px;
  border: solid 6px #A4CD92;
  font-size: 15px;
  margin-bottom: 25px;
}

.home-content {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100vw;
  overflow: hidden;
}

.expenses,.incomes{
  background-color: #ebe9fb;
  border: none;
  color: black;
  margin-left: 20px;
  font-weight: bold;
  font-size: 16px;
  font-family: Arial,Serif;
  appearance: textfield;
}

.dato {
  padding: 15px;
}

label {
  font-family: Arial,serif;
}

#startDate, #endDate {
  padding: 10px;
  text-align: center;
  border-radius: 30px;
  min-width: 120px;
  font-size: 13px;
}

.updateButton {
  background-color: #A4CD92;
  min-width: 150px;
  border-radius: 30px;
  text-align: center;
  min-height: 50px;
  padding: 10px;
  margin: 20px;
  border: none;
  font-size: 16px;
}

p {
  font-weight: bold;
}

.dates {
  max-height: 300px;
  justify-content: center;
  align-items: center;
}

.doughnutChart {
  width: auto;
  max-height: 500px;
}

.top {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  font-size: 24px;
  width: 100vw;
  margin-bottom: -70px;
}

.bottomHalf {
  width: 100vw;
}

.text{
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  font-family: Arial,Serif;
}

button:hover {
  background-color: #89a76c !important;
}

@media screen and (max-width: 700px) {
  .home-content {
    flex-direction: column;
    align-items: center;
  }

  .dates, .doughnutChart, .footer {
    width: 100%;
  }

  .doughnutChart{
    margin-bottom: 50px;
  }

  .dates {
    display: flex;
    flex-direction: column;
    align-items: center;
  }

  .dates label,
  .dates input[type="date"],
  .dates button {
    display: block;
    margin-bottom: 10px;
  }
}

@media screen and (max-width: 1000px) {
  .question{
    right: 30%;
  }
  .question-expanded{
    right: -4%;
  }
}
@media screen and (max-width: 800px) {
  .question{
    right: 25%;
  }
  .question-expanded{
    right: -17%;
  }
}
@media screen and (max-width: 700px) {
  .question{
    right: 20%;
    top: 76%;
  }
  .question-expanded{
    right: -15%;
  }
}
@media screen and (max-width: 500px) {
  .question{
    right: 15%;
    top: 77%;
  }
  .question-expanded{
    right: -15%;
  }
}
@media screen and (max-width: 420px) {
  .question{
    right: 10%;
    top: 77%;
  }
  .question-expanded{
    right: -17%;
  }
}
@media screen and (max-width: 378px) {
  .question{
    right: 5%;
    top: 77%;
  }
  .question-expanded{
    right: -30%;
  }
}
@media screen and (min-width: 700px) {
  .home-content{
    margin-top: -50px;
  }
}
.error {
  color: red;
  font-size: 20px;
  font-style: italic;
}
</style>