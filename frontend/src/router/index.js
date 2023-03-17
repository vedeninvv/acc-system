import Vue from 'vue'
import VueRouter from 'vue-router'
import ContractsPage from "@/views/ContractsPage";
import CounterpartiesPage from "@/views/СounterpartiesPage"
import ReportsPage from "@/views/ReportsPage";
import AdministrationPage from "@/views/AdministrationPage";
import LoginPage from "@/views/LoginPage";

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        redirect: '/contracts'
    },
    {
        path: '/signin',
        component: LoginPage
    },
    {
        path: '/contracts',
        component: ContractsPage
    },
    {
        path: '/counterparties',
        component: CounterpartiesPage
    },
    {
        path: '/reports',
        component: ReportsPage
    },
    {
        path: '/administration',
        component: AdministrationPage
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
