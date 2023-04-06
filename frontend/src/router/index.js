import Vue from 'vue'
import VueRouter from 'vue-router'
import ContractsPage from "@/views/ContractsPage";
import CounterpartiesPage from "@/views/СounterpartiesPage"
import ReportsPage from "@/views/ReportsPage";
import AdministrationPage from "@/views/AdministrationPage";
import LoginPage from "@/views/LoginPage";
import {isAuthenticated} from "@/shared/services/userService";
import ContractFormPage from "@/views/ContractFormPage";

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        redirect: '/contracts',
    },
    {
        path: '/signin',
        component: LoginPage,
    },
    {
        path: '/contracts',
        component: ContractsPage,
    },
    {
        path: '/contracts/:id',
        component: ContractFormPage
    },
    {
        path: 'new',
        component: ContractFormPage
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

router.beforeEach(async (to, from, next) => {
    if (to.path !== '/signin' && !(await isAuthenticated())) {
        next({
            path: 'signin',
            replace: true
        })
    } else {
        next()
    }
})

export default router
