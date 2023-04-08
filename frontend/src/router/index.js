import Vue from 'vue'
import VueRouter from 'vue-router'
import ContractsPage from "@/views/ContractsPage";
import CounterpartiesPage from "@/views/СounterpartiesPage"
import ReportsPage from "@/views/ReportsPage";
import AdministrationPage from "@/views/AdministrationPage";
import LoginPage from "@/views/LoginPage";
import {isAuthenticated} from "@/shared/services/userService";
import ContractUpdatePage from "@/views/ContractUpdatePage";
import NotFoundPage from "@/views/NotFoundPage";

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
        path: '/contracts/:id(\\d+)',
        component: ContractUpdatePage
    },
    {
        path: '/contracts/new',
        component: ContractUpdatePage
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
    },
    {
        path: '/404',
        component: NotFoundPage
    },
    {
        path: '*',
        redirect: '/404'
    },
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
