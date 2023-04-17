import Vue from 'vue'
import VueRouter from 'vue-router'
import ContractsPage from "@/views/ContractsPage";
import CounterpartiesPage from "@/views/СounterpartiesPage"
import ReportsPage from "@/views/ReportsPage";
import AdministrationPage from "@/views/AdministrationPage";
import LoginPage from "@/views/LoginPage";
import {getRole, isAuthenticated, roles} from "@/shared/services/userService";
import ContractCardPage from "@/views/ContractCardPage";
import NotFoundPage from "@/views/NotFoundPage";
import CounterpartyContractCardPage from "@/views/CounterpartyContractCardPage";
import ContractStageCardPage from "@/views/ContractStageCardPage";
import CounterpartyCardPage from "@/views/CounterpartyCardPage";
import ForbiddenPage from "@/views/ForbiddenPage";
import UserCardPage from "@/views/UserCardPage";

Vue.use(VueRouter)

function isAdmin(to, from, next) {
    if (getRole() === roles.admin) {
        next()
    } else {
        router.push("/404")
    }
}

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
        component: ContractCardPage
    },
    {
        path: '/contracts/:contractId(\\d+)/counterparty-contracts/:id(\\d+)',
        component: CounterpartyContractCardPage
    },
    {
        path: '/contracts/:contractId(\\d+)/counterparty-contracts/new',
        component: CounterpartyContractCardPage
    },
    {
        path: '/contracts/:contractId(\\d+)/stages/:id(\\d+)',
        component: ContractStageCardPage
    },
    {
        path: '/contracts/:contractId(\\d+)/stages/new',
        component: ContractStageCardPage
    },
    {
        path: '/contracts/new',
        component: ContractCardPage
    },
    {
        path: '/counterparties',
        component: CounterpartiesPage
    },
    {
        path: '/counterparties/:id(\\d+)',
        component: CounterpartyCardPage
    },
    {
        path: '/counterparties/new',
        component: CounterpartyCardPage
    },
    {
        path: '/reports',
        component: ReportsPage
    },
    {
        path: '/administration',
        component: AdministrationPage,
        beforeEnter: isAdmin,
    },
    {
        path: '/users/:id(\\d+)',
        component: UserCardPage
    },
    {
        path: '/users/new',
        component: UserCardPage,
        beforeEnter: isAdmin,
    },
    {
        path: '/users',
        redirect: '/administration'
    },
    {
        path: '/403',
        component: ForbiddenPage
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
